package top.wetabq.easyapi.api.default

import cn.nukkit.Server
import cn.nukkit.plugin.Plugin
import top.wetabq.easyapi.api.CommonDynamicIntegrateAPI
import top.wetabq.easyapi.task.PluginTaskEntry

class PluginTaskAPI<T: Plugin>(private val plugin: Plugin) : CommonDynamicIntegrateAPI<PluginTaskEntry<T>, PluginTaskAPI<T>>() {

    override fun addInterface(t: PluginTaskEntry<T>): PluginTaskAPI<T> {
        Server.getInstance().scheduler.scheduleDelayedRepeatingTask(plugin, t.pluginTask, t.delay, t.period)
        return this
    }

    override fun removeInterface(t: PluginTaskEntry<T>): PluginTaskAPI<T> {
        Server.getInstance().scheduler.cancelTask(t.pluginTask.taskId)
        return this
    }


}