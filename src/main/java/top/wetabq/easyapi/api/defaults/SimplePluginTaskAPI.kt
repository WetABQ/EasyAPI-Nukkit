/*
 * Copyright (c) 2020 WetABQ and contributors
 *
 *  此源代码的使用受 GNU GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU GPLv3 license that can be found through the following link.
 *
 * https://github.com/WetABQ/EasyAPI-Nukkit/blob/master/LICENSE
 */

package top.wetabq.easyapi.api.defaults

import cn.nukkit.scheduler.PluginTask
import top.wetabq.easyapi.EasyAPI
import top.wetabq.easyapi.api.SimpleIntegrateAPI

object SimplePluginTaskAPI : SimpleIntegrateAPI {

    fun delay(delay: Int, action: (PluginTask<EasyAPI>, Int) -> Unit): PluginTask<EasyAPI> {
        val task =
            object: PluginTask<EasyAPI>(EasyAPI.INSTANCE) {

                override fun onRun(currentTick: Int) {
                    action(this, currentTick)
                }
            }
        EasyAPI.server.scheduler.scheduleDelayedTask(task, delay)
        return task
    }

    fun repeating(period: Int, action: (PluginTask<EasyAPI>, Int) -> Unit): PluginTask<EasyAPI> {
        val task =
            object: PluginTask<EasyAPI>(EasyAPI.INSTANCE) {

                override fun onRun(currentTick: Int) {
                    action(this, currentTick)
                }
            }
        EasyAPI.server.scheduler.scheduleRepeatingTask(task, period)
        return task
    }

    fun delayRepeating(delay: Int, period: Int, action: (PluginTask<EasyAPI>, Int) -> Unit): PluginTask<EasyAPI> {
        val task =
            object: PluginTask<EasyAPI>(EasyAPI.INSTANCE) {

                override fun onRun(currentTick: Int) {
                    action(this, currentTick)
                }
            }
        EasyAPI.server.scheduler.scheduleDelayedRepeatingTask(task, delay, period)
        return task
    }


}