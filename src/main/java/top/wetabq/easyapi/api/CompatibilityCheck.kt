/*
 * Copyright (c) 2020 WetABQ and contributors
 *
 *  此源代码的使用受 GNU GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU GPLv3 license that can be found through the following link.
 *
 * https://github.com/WetABQ/EasyAPI-Nukkit/blob/master/LICENSE
 */

package top.wetabq.easyapi.api

import top.wetabq.easyapi.EasyAPI

class CompatibilityCheck(private val providers: List<String>) {

    private var finalKey = "-1"

    fun check() {
        // TODO: Check server started
        providers.forEach { plugin ->
            if (EasyAPI.server.pluginManager.getPlugin(plugin) != null) {
                finalKey = plugin
            }
        }
        if (finalKey == "-1") finalKey = ""
    }


    fun <T> doCompatibilityAction(compatibleAction: Map<String, () -> T>): T? {
        var finalValue: T? = null
        if (finalKey == "-1") check()
        if (finalKey != "") {
            compatibleAction.forEach { (key, action) ->
                if (key == finalKey)  {
                    finalValue = action()
                }
            }
        } else throw CompatibilityException("don't find any compatible provider to do action")
        return finalValue
    }

    fun isCompatible(): Boolean {
        if (finalKey == "-1") check()
        return (finalKey != "")
    }

}