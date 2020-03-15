package top.wetabq.easyapi

import cn.nukkit.plugin.PluginBase
import top.wetabq.easyapi.module.EasyAPIModuleManager
import top.wetabq.easyapi.utils.MerticsLite
import top.wetabq.easyapi.utils.color

object EasyAPI : PluginBase() {


    val TITLE = "&c[&eEasy&aAPI&c]".color()

    val VERSION: String = this.description.version

    val moduleManager = EasyAPIModuleManager

    override fun onEnable() {
        MerticsLite(this)
        moduleManager.registerDefault()
        //logger.info("EasyAPI by WetABQ Enabled...")
    }

}