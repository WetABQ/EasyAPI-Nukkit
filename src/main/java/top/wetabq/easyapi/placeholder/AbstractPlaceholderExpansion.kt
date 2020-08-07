/*
 * Copyright (c) 2020 WetABQ and contributors
 *
 *  此源代码的使用受 GNU GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU GPLv3 license that can be found through the following link.
 *
 * https://github.com/WetABQ/EasyAPI-Nukkit/blob/master/LICENSE
 */

package top.wetabq.easyapi.placeholder

import top.wetabq.easyapi.module.defaults.PlaceholderManager

abstract class AbstractPlaceholderExpansion : PlaceholderExpansion {

    fun isRegistered(): Boolean = PlaceholderManager.getExpansion(this.getIdentifier()) != null

    fun register(): PlaceholderExpansion {
        PlaceholderManager.registerPlaceholderExpansion(this)
        return this
    }

    fun unregister(): PlaceholderExpansion {
        PlaceholderManager.unregisterPlaceholderExpansion(this.getIdentifier())
        return this
    }

    override fun onUnregister() {

    }

    override fun getValueIdentifierList(): Collection<String> = getPlaceholderDescription().keys

    override fun getPlaceholderExpression(valueIdentifier: String): String = valueIdentifier.getPlaceholderExpression(getIdentifier())

}