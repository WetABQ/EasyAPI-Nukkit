package top.wetabq.easyapi.api.defaults

import cn.nukkit.plugin.Plugin
import cn.nukkit.scheduler.AsyncTask
import top.wetabq.easyapi.EasyAPI
import top.wetabq.easyapi.api.CommonDynamicIntegrateAPI

class AsyncTaskAPI(private val plugin: Plugin) : CommonDynamicIntegrateAPI<AsyncTask, AsyncTaskAPI>() {

    override fun addInterface(t: AsyncTask): AsyncTaskAPI {
        EasyAPI.server.scheduler.scheduleAsyncTask(plugin, t)
        return this
    }

    override fun removeInterface(t: AsyncTask): AsyncTaskAPI {
        if (!t.isFinished) EasyAPI.server.scheduler.cancelTask(t.taskId)
        return this
    }


}