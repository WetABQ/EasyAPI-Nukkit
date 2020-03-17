package top.wetabq.easyapi.task

import cn.nukkit.plugin.Plugin
import cn.nukkit.scheduler.PluginTask

data class PluginTaskEntry<T: Plugin> (
    var pluginTask: PluginTask<T>,
    var period: Int,
    var delay: Int = 0
)