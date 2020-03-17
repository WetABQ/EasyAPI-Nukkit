package top.wetabq.easyapi

import cn.nukkit.plugin.PluginBase
import top.wetabq.easyapi.module.EasyAPIModuleManager
import top.wetabq.easyapi.utils.MerticsLite
import top.wetabq.easyapi.utils.color
import java.util.*


object EasyAPI : PluginBase() {


    var TITLE = "&c[&eEasy&aAPI&c]".color()
    var git = getGitInfo()

    val VERSION: String = this.description.version
    var GIT_VERSION = getVersion()

    val moduleManager = EasyAPIModuleManager

    override fun onEnable() {
        MerticsLite(this)
        moduleManager.registerDefault()
        //logger.info("EasyAPI by WetABQ Enabled...")
    }

    override fun onDisable() {
        moduleManager.disableAll()
    }

    private fun getGitInfo(): Properties {
        val gitFileStream = EasyAPI.javaClass.classLoader.getResourceAsStream("git.properties")
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