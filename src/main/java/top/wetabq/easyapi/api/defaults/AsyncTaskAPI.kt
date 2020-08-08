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