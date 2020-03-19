package top.wetabq.easyapi.api.defaults

import cn.nukkit.scheduler.AsyncTask
import top.wetabq.easyapi.EasyAPI
import top.wetabq.easyapi.api.SimpleIntegrateAPI

object SimpleAsyncTaskAPI : SimpleIntegrateAPI {

    fun add(action: (AsyncTask) -> Unit): AsyncTask {
        val task =
            object: AsyncTask() {
                override fun onRun() {
                    action(this)
                }
            }
        EasyAPI.server.scheduler.scheduleAsyncTask(EasyAPI.INSTANCE, task)
        return task
    }


}