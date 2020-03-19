package top.wetabq.easyapi.utils

import cn.nukkit.plugin.Plugin
import cn.nukkit.utils.Config
import com.google.common.base.Preconditions
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import top.wetabq.easyapi.EasyAPI
import top.wetabq.easyapi.api.defaults.SimpleAsyncTaskAPI
import top.wetabq.easyapi.api.defaults.SimplePluginTaskAPI
import java.io.*
import java.nio.charset.StandardCharsets
import java.util.*
import java.util.zip.GZIPOutputStream
import javax.net.ssl.HttpsURLConnection

class MerticsLite(var plugin: Plugin) {

    // Is bStats enabled on this server?
    private var enabled = false

    // Should failed requests be logged?
    private var logFailedRequests = false

    // Should the sent data be logged?
    private var logSentData = false

    // Should the response text be logged?
    private var logResponseStatusText = false

    // The uuid of the server
    private var serverUUID: String? = null

    init {
        // Get the config file
        val bStatsFolder = File(plugin.dataFolder.parentFile, "bStats")
        val configFile = File(bStatsFolder, "config.yml")
        val config = Config(configFile)
        // Check the config
        val map = config.all as LinkedHashMap<String, Any>
        // Every server gets it's unique random id.
        if (!config.isString("serverUuid")) {
            map["serverUuid"] = UUID.randomUUID().toString()
        } else {
            try { // Check the UUID
                UUID.fromString(config.getString("serverUuid"))
            } catch (ignored: Exception) {
                map["serverUuid"] = UUID.randomUUID().toString()
            }
        }
        // Add default values
        if (!config.isBoolean("enabled")) {
            map["enabled"] = true
        }
        // Should failed request be logged?
        if (!config.isBoolean("logFailedRequests")) {
            map["logFailedRequests"] = false
        }
        // Should the sent data be logged?
        if (!config.isBoolean("logSentData")) {
            map["logSentData"] = false
        }
        // Should the response text be logged?
        if (!config.isBoolean("logResponseStatusText")) {
            map["logResponseStatusText"] = false
        }
        config.setAll(map)
        config.save()
        // Load the data
        enabled = config.getBoolean("enabled", true)
        serverUUID = config.getString("serverUuid")
        logFailedRequests = config.getBoolean("logFailedRequests", false)
        logSentData = config.getBoolean("logSentData", false)
        logResponseStatusText = config.getBoolean("logResponseStatusText", false)
        if (enabled) {
            SimplePluginTaskAPI.delayRepeating(20 * 60 * 5, 20 * 60 * 30) { task, _ ->
                if (!plugin.isEnabled) { // Plugin was disabled
                    task.cancel()
                } else {
                    submitData()
                }
            }
        }
    }


    /**
     * Gets the plugin specific data.
     * This method is called using Reflection.
     *
     * @return The plugin specific data.
     */
    fun getPluginData(): JsonObject {
        val data = JsonObject()
        val pluginName = plugin.name
        val pluginVersion = plugin.description.version
        data.addProperty("pluginName", pluginName) // Append the name of the plugin
        data.addProperty("pluginVersion", pluginVersion) // Append the version of the plugin
        val customCharts = JsonArray()
        data.add("customCharts", customCharts)
        return data
    }

    /**
     * Gets the server specific data.
     *
     * @return The server specific data.
     */
    private fun getServerData(): JsonObject { // Minecraft specific data
        val playerAmount = EasyAPI.server.onlinePlayers.size
        val onlineMode = if (EasyAPI.server.getPropertyBoolean("xbox-auth", false)) 1 else 0
        val minecraftVersion = EasyAPI.server.version
        val softwareName = EasyAPI.server.name
        // OS/Java specific data
        val javaVersion = System.getProperty("java.version")
        val osName = System.getProperty("os.name")
        val osArch = System.getProperty("os.arch")
        val osVersion = System.getProperty("os.version")
        val coreCount = Runtime.getRuntime().availableProcessors()
        val data = JsonObject()
        data.addProperty("serverUUID", serverUUID)
        data.addProperty("playerAmount", playerAmount)
        data.addProperty("onlineMode", onlineMode)
        data.addProperty("bukkitVersion", minecraftVersion)
        data.addProperty("bukkitName", softwareName)
        data.addProperty("javaVersion", javaVersion)
        data.addProperty("osName", osName)
        data.addProperty("osArch", osArch)
        data.addProperty("osVersion", osVersion)
        data.addProperty("coreCount", coreCount)
        return data
    }

    /**
     * Collects the data and sends it afterwards.
     */
    private fun submitData() {
        val data = getServerData()
        val pluginData = JsonArray()
        pluginData.add(getPluginData() as JsonElement)
        data.add("plugins", pluginData)
        SimpleAsyncTaskAPI.add {
            try { // Send the data
                sendData(plugin, data)
            } catch (e: Exception) { // Something went wrong! :(
                if (logFailedRequests) {
                    plugin.logger.warning("Could not submit plugin stats of " + plugin.name, e)
                }
            }
        }
    }

    /**
     * Sends the data to the bStats server.
     *
     * @param plugin Any plugin. It's just used to get a logger instance.
     * @param data The data to send.
     * @throws Exception If the request failed.
     */
    @Throws(Exception::class)
    private fun sendData(plugin: Plugin, data: JsonObject) {
        Preconditions.checkNotNull(data)
        if (EasyAPI.server.isPrimaryThread) {
            throw IllegalAccessException("This method must not be called from the main thread!")
        }
        if (logSentData) {
            plugin.logger.info("Sending data to bStats: $data")
        }
        val connection =
            java.net.URL(URL).openConnection() as HttpsURLConnection
        // Compress the data to save bandwidth
        val compressedData = compress(data.toString())
        // Add headers
        connection.requestMethod = "POST"
        connection.addRequestProperty("Accept", "application/json")
        connection.addRequestProperty("Connection", "close")
        connection.addRequestProperty("Content-Encoding", "gzip") // We gzip our request
        connection.addRequestProperty("Content-Length", compressedData.size.toString())
        connection.setRequestProperty("Content-Type", "application/json") // We send our data in JSON format
        connection.setRequestProperty("User-Agent", "MC-Server/$B_STATS_VERSION")
        // Send data
        connection.doOutput = true
        DataOutputStream(connection.outputStream).use { outputStream ->
            outputStream.write(compressedData)
            outputStream.flush()
        }
        val inputStream = connection.inputStream
        val builder = StringBuilder()
        BufferedReader(InputStreamReader(inputStream)).use { bufferedReader ->
            var line: String?
            while (bufferedReader.readLine().also { line = it } != null) {
                builder.append(line)
            }
        }
        if (logResponseStatusText) {
            plugin.logger.info("Sent data to bStats and received response: $builder")
        }
    }

    /**
     * Gzips the given String.
     *
     * @param str The string to gzip.
     * @return The gzipped String.
     * @throws IOException If the compression failed.
     */
    @Throws(IOException::class)
    private fun compress(str: String): ByteArray {
        val outputStream = ByteArrayOutputStream()
        GZIPOutputStream(outputStream).use { gzip -> gzip.write(str.toByteArray(StandardCharsets.UTF_8)) }
        return outputStream.toByteArray()
    }

    companion object {

        // The version of this bStats class
        const val B_STATS_VERSION = 1

        // The url to which the data is sent
        private val URL = "https://bStats.org/submitData/bukkit"

    }


}