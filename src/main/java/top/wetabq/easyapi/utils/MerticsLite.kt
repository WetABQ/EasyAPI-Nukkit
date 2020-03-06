package top.wetabq.easyapi.utils

import cn.nukkit.Server
import cn.nukkit.plugin.Plugin
import cn.nukkit.plugin.service.NKServiceManager
import cn.nukkit.plugin.service.RegisteredServiceProvider
import cn.nukkit.plugin.service.ServicePriority
import cn.nukkit.utils.Config
import com.google.common.base.Preconditions
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.io.*
import java.lang.reflect.Field
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Modifier
import java.nio.charset.StandardCharsets
import java.util.*
import java.util.function.Consumer
import java.util.zip.GZIPOutputStream
import javax.net.ssl.HttpsURLConnection

class MerticsLite(var plugin: Plugin) {

    // The version of this bStats class
    val B_STATS_VERSION = 1

    // The url to which the data is sent
    private val URL = "https://bStats.org/submitData/bukkit"

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

    /**
     * Class constructor.
     *
     * @param plugin The plugin which stats should be submitted.
     */
    init {
        Preconditions.checkNotNull(plugin)
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
            var found = false
            // Search for all other bStats Metrics classes to see if we are the first one
            for (service in Server.getInstance().serviceManager.knownService) {
                try {
                    service.getField("B_STATS_VERSION") // Our identifier :)
                    found = true // We aren't the first
                    break
                } catch (ignored: NoSuchFieldException) {
                }
            }
            // Register our service
            Server.getInstance().serviceManager.register(MerticsLite::class.java, this, plugin, ServicePriority.NORMAL)
            if (!found) { // We are the first!
                startSubmitting()
            }
        }
    }

    /**
     * Checks if bStats is enabled.
     *
     * @return Whether bStats is enabled or not.
     */
    fun isEnabled(): Boolean {
        return enabled
    }

    /**
     * Starts the Scheduler which submits our data every 30 minutes.
     */
    private fun startSubmitting() {
        val timer = Timer(true) // We use a timer cause want to be independent from the server tps
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                if (!plugin.isEnabled) { // Plugin was disabled
                    timer.cancel()
                    return
                }
                // Nevertheless we want our code to run in the Nukkit main thread, so we have to use the Nukkit scheduler
                // Don't be afraid! The connection to the bStats server is still async, only the stats collection is sync ;)
                Server.getInstance().scheduler.scheduleTask(plugin) { submitData() }
            }
        }, 1000 * 60 * 5.toLong(), 1000 * 60 * 30.toLong())
        // Submit the data every 30 minutes, first time after 5 minutes to give other plugins enough time to start
        // WARNING: Changing the frequency has no effect but your plugin WILL be blocked/deleted!
        // WARNING: Just don't do it!
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
        val playerAmount = Server.getInstance().onlinePlayers.size
        val onlineMode = if (Server.getInstance().getPropertyBoolean("xbox-auth", false)) 1 else 0
        val minecraftVersion = Server.getInstance().version
        val softwareName = Server.getInstance().name
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
        // Search for all other bStats Metrics classes to get their plugin data
        Server.getInstance().serviceManager.knownService.forEach { service ->
            try {
                service.getField("B_STATS_VERSION") // Our identifier :)
                var providers: List<RegisteredServiceProvider<*>>? = null
                try {
                    val field = Field::class.java.getDeclaredField("modifiers")
                    field.isAccessible = true
                    val handle = NKServiceManager::class.java.getDeclaredField("handle")
                    field.setInt(handle, handle.modifiers and Modifier.FINAL.inv())
                    handle.isAccessible = true
                    providers = (handle[Server.getInstance().serviceManager] as Map<Class<*>?, List<RegisteredServiceProvider<*>>?>)[service]
                } catch (e: IllegalAccessException) { // Something went wrong! :(
                    if (logFailedRequests) {
                        plugin.logger.warning("Failed to link to metrics class " + service.name, e)
                    }
                } catch (e: IllegalArgumentException) {
                    if (logFailedRequests) {
                        plugin.logger.warning("Failed to link to metrics class " + service.name, e)
                    }
                } catch (e: SecurityException) {
                    if (logFailedRequests) {
                        plugin.logger.warning("Failed to link to metrics class " + service.name, e)
                    }
                }
                if (providers != null) {
                    for (provider in providers) {
                        try {
                            val plugin = provider.service.getMethod("getPluginData").invoke(provider.provider)
                            if (plugin is JsonObject) {
                                pluginData.add(plugin as JsonElement)
                            }
                        } catch (ignored: SecurityException) {
                        } catch (ignored: NoSuchMethodException) {
                        } catch (ignored: IllegalAccessException) {
                        } catch (ignored: IllegalArgumentException) {
                        } catch (ignored: InvocationTargetException) {
                        }
                    }
                }
            } catch (ignored: NoSuchFieldException) {
            }
        }
        data.add("plugins", pluginData)
        // Create a new thread for the connection to the bStats server
        Thread(Runnable {
            try { // Send the data
                sendData(plugin, data)
            } catch (e: Exception) { // Something went wrong! :(
                if (logFailedRequests) {
                    plugin.logger.warning("Could not submit plugin stats of " + plugin.name, e)
                }
            }
        }).start()
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
        if (Server.getInstance().isPrimaryThread) {
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


}