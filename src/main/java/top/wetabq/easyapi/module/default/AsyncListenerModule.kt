package top.wetabq.easyapi.module.default

import cn.nukkit.event.Event
import cn.nukkit.plugin.Plugin
import cn.nukkit.scheduler.AsyncTask
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import top.wetabq.easyapi.EasyAPI
import top.wetabq.easyapi.module.ModuleInfo
import top.wetabq.easyapi.module.ModuleVersion
import top.wetabq.easyapi.module.SimpleEasyAPIModule

// WIP Not Easy

suspend fun <T: Event> coroutineCallEvent(event: T, syncAction: (T) -> Unit = {}, asyncAction: (T) -> Unit) {
    syncAction(event)
    coroutineScope {
        launch(Dispatchers.Default) {
            asyncAction(event)
        }
    }
}

fun <T: Event> asyncTaskCallEvent(event: T, plugin: Plugin, syncAction: (T) -> Unit = {}, asyncAction: (T) -> Unit) {
    syncAction(event)
    EasyAPI.server.scheduler.scheduleAsyncTask(plugin, object: AsyncTask() {
        override fun onRun() {
            asyncAction(event)
        }
    })
}

object AsyncListenerModule: SimpleEasyAPIModule() {

    const val MODULE_NAME = "AsyncListenerModule"
    const val AUTHOR = "WetABQ"

    override fun getModuleInfo(): ModuleInfo = ModuleInfo(
        EasyAPI,
        MODULE_NAME,
        AUTHOR,
        ModuleVersion(1, 0 ,0)
    )

    override fun moduleRegister() {

    }

    override fun moduleDisable() {

    }

}