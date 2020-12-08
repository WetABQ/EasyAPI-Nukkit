/*
 * Copyright (c) 2020 WetABQ and contributors
 *
 *  此源代码的使用受 GNU GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU GPLv3 license that can be found through the following link.
 *
 * https://github.com/WetABQ/EasyAPI-Nukkit/blob/master/LICENSE
 */

package top.wetabq.easyapi

import cn.nukkit.Server
import cn.nukkit.plugin.PluginBase
import com.fundebug.Fundebug
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
        lateinit var fundebug: Fundebug


        var TITLE = "&c[&eEasy&aAPI&c]".color()

        val moduleManager = EasyAPIModuleManager

        fun init() {
            fundebug = Fundebug("407175d08d481cdaff942a00ceb4933455a4afb1d1cdc6eabc641249343382ff")
            //fundebug.notify("Enable", "The plugin has been running at server UUID:${MerticsLite.serverUUID}")
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