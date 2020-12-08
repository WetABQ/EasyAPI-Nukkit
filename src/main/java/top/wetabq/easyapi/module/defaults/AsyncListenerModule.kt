/*
 * Copyright (c) 2020 WetABQ and contributors
 *
 *  此源代码的使用受 GNU GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU GPLv3 license that can be found through the following link.
 *
 * https://github.com/WetABQ/EasyAPI-Nukkit/blob/master/LICENSE
 */

package top.wetabq.easyapi.module.defaults

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
        EasyAPI.INSTANCE,
        MODULE_NAME,
        AUTHOR,
        ModuleVersion(1, 0 ,0)
    )

    override fun moduleRegister() {

    }

    override fun moduleDisable() {

    }

}