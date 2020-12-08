/*
 * Copyright (c) 2020 WetABQ and contributors
 *
 *  此源代码的使用受 GNU GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU GPLv3 license that can be found through the following link.
 *
 * https://github.com/WetABQ/EasyAPI-Nukkit/blob/master/LICENSE
 */

package top.wetabq.easyapi.api.defaults

import cn.nukkit.plugin.Plugin
import top.wetabq.easyapi.EasyAPI
import top.wetabq.easyapi.api.CommonDynamicIntegrateAPI
import top.wetabq.easyapi.task.PluginTaskEntry

class PluginTaskAPI<T: Plugin>(private val plugin: Plugin) : CommonDynamicIntegrateAPI<PluginTaskEntry<T>, PluginTaskAPI<T>>() {

    override fun addInterface(t: PluginTaskEntry<T>): PluginTaskAPI<T> {
        EasyAPI.server.scheduler.scheduleDelayedRepeatingTask(plugin, t.pluginTask, t.delay, t.period)
        return this
    }

    override fun removeInterface(t: PluginTaskEntry<T>): PluginTaskAPI<T> {
        EasyAPI.server.scheduler.cancelTask(t.pluginTask.taskId)
        return this
    }


}