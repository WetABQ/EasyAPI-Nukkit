package top.wetabq.easyapi

import cn.nukkit.Server
import cn.nukkit.plugin.PluginBase
import top.wetabq.easyapi.module.EasyAPIModuleManager
import top.wetabq.easyapi.utils.MerticsLite
import top.wetabq.easyapi.utils.color
import java.util.*


class EasyAPI : PluginBase() {

    override fun onLoad() {
        INSTANCE = this
        init()
    }

    override fun onEnable() {
        MerticsLite(this)
        moduleManager.registerDefault()
        logger.info("EasyAPI Verison:$VERSION $GIT_VERSION by WetABQ Enabled...")
    }

    override fun onDisable() {
        moduleManager.disableAll()
    }

    companion object {

        lateinit var git: Properties
        lateinit var server: Server

        lateinit var INSTANCE: EasyAPI
        lateinit var VERSION: String
        lateinit var GIT_VERSION: String

        var TITLE = "&c[&eEasy&aAPI&c]".color()

        val moduleManager = EasyAPIModuleManager

        fun init() {
            server = INSTANCE.server
            git = getGitInfo()
            VERSION = INSTANCE.description.version
            GIT_VERSION = getVersion()
        }

        private fun getGitInfo(): Properties {
            val gitFileStream = INSTANCE::class.java.classLoader.getResourceAsStream("git.properties")
            val properties = Properties()
            properties.load(gitFileStream)
            return properties
        }

        private fun getVersion(): String {
            val version = StringBuilder()
            version.append("git-")
            var commitId: String
            return if (git.getProperty("git.commit.id.abbrev").also {
                    commitId = it
                } == null) {
                version.append("null").toString()
            } else version.append(commitId).toString()
        }

    }

}