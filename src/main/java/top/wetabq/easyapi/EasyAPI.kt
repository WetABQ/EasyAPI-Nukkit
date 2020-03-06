package top.wetabq.easyapi

import cn.nukkit.plugin.PluginBase
import top.wetabq.easyapi.utils.MerticsLite

class EasyAPI : PluginBase() {

    override fun onEnable() {
        MerticsLite(this)
        logger.info("EasyAPI by WetABQ Enabled...")
    }

}