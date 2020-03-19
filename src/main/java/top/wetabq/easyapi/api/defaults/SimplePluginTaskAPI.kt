package top.wetabq.easyapi.api.defaults

import cn.nukkit.scheduler.PluginTask
import top.wetabq.easyapi.EasyAPI
import top.wetabq.easyapi.api.SimpleIntegrateAPI

object SimplePluginTaskAPI : SimpleIntegrateAPI {

    fun delay(delay: Int, action: (Int) -> Unit): PluginTask<EasyAPI> {
        val task =
            object: PluginTask<EasyAPI>(EasyAPI.INSTANCE) {

                override fun onRun(currentTick: Int) {
                    action(currentTick)
                }
            }
        EasyAPI.server.scheduler.scheduleDelayedTask(task, delay)
        return task
    }

    fun repeating(period: Int, action: (Int) -> Unit): PluginTask<EasyAPI> {
        val task =
            object: PluginTask<EasyAPI>(EasyAPI.INSTANCE) {

                override fun onRun(currentTick: Int) {
                    action(currentTick)
                }
            }
        EasyAPI.server.scheduler.scheduleRepeatingTask(task, period)
        return task
    }

    fun delayRepeating(delay: Int, period: Int, action: (Int) -> Unit): PluginTask<EasyAPI> {
        val task =
            object: PluginTask<EasyAPI>(EasyAPI.INSTANCE) {

                override fun onRun(currentTick: Int) {
                    action(currentTick)
                }
            }
        EasyAPI.server.scheduler.scheduleDelayedRepeatingTask(task, delay, period)
        return task
    }


}