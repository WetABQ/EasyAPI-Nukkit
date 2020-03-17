package top.wetabq.easyapi.api.default

import cn.nukkit.Server
import cn.nukkit.plugin.Plugin
import cn.nukkit.scheduler.AsyncTask
import top.wetabq.easyapi.api.CommonDynamicIntegrateAPI

class AsyncTaskAPI(private val plugin: Plugin) : CommonDynamicIntegrateAPI<AsyncTask, AsyncTaskAPI>() {

    override fun addInterface(t: AsyncTask): AsyncTaskAPI {
        Server.getInstance().scheduler.scheduleAsyncTask(plugin, t)
        return this
    }

    override fun removeInterface(t: AsyncTask): AsyncTaskAPI {
        Server.getInstance().scheduler.scheduleAsyncTask(plugin, t)
        return this
    }


}