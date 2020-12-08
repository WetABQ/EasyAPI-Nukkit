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
import top.wetabq.easyapi.api.CommonDynamicIntegrateAPI
import top.wetabq.easyapi.api.DisableNotRemoveAll
import top.wetabq.easyapi.config.defaults.SimpleConfig
import top.wetabq.easyapi.config.defaults.SimpleConfigEntry

@DisableNotRemoveAll
class SimpleConfigAPI(private val plugin: Plugin): CommonDynamicIntegrateAPI<SimpleConfigEntry<*>, SimpleConfigAPI>() {

    override fun addInterface(t: SimpleConfigEntry<*>): SimpleConfigAPI {
        SimpleConfig.addPath(t.path, plugin, t.value?:"")
        SimpleConfig.save()
        return this
    }

    override fun removeInterface(t: SimpleConfigEntry<*>): SimpleConfigAPI {
        SimpleConfig.removePath(t.path, plugin)
        SimpleConfig.save()
        return this
    }

    fun getPath(path: String): String {
        return SimpleConfig.getPath(path, plugin)
    }

    fun getPathValue(path: String): Any? {
        return SimpleConfig.getPathValue(path, plugin)
    }

    fun directlyGetPathValue(path: String): Any? {
        return SimpleConfig.directlyGetPathValue(path)
    }

    fun setPathValue(key: SimpleConfigEntry<*>) {
        SimpleConfig.setPathValue(key.path, plugin, key.value?:"")
    }



}