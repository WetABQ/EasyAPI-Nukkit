package top.wetabq.easyapi.api.defaults

import cn.nukkit.scheduler.AsyncTask
import top.wetabq.easyapi.EasyAPI
import top.wetabq.easyapi.api.SimpleIntegrateAPI

object SimpleAsyncTaskAPI : SimpleIntegrateAPI {

    fun add(action: () -> Unit): AsyncTask {
        val task =
            object: AsyncTask() {
                override fun onRun() {
                    action()
                }
            }
        EasyAPI.server.scheduler.scheduleAsyncTask(EasyAPI.INSTANCE, task)
        return task
    }


}