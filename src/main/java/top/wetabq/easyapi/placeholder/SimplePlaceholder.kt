/*
 * Copyright (c) 2020 WetABQ and contributors
 *
 *  此源代码的使用受 GNU GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU GPLv3 license that can be found through the following link.
 *
 * https://github.com/WetABQ/EasyAPI-Nukkit/blob/master/LICENSE
 */

package top.wetabq.easyapi.placeholder

import cn.nukkit.Player
import top.wetabq.easyapi.module.IEasyAPIModule

class SimplePlaceholder(
    private val owner: IEasyAPIModule,
    private val identifier: String,
    private inline val onRequestFunc: (Player?, String) -> String?,
    private val placeholderDescription: LinkedHashMap<String, String> = linkedMapOf(),
    private inline val canRegisterFunc: () -> Boolean = {true}
): AbstractPlaceholderExpansion() {

    override fun canRegister(): Boolean = canRegisterFunc()

    override fun getOwner(): IEasyAPIModule = owner

    override fun getIdentifier(): String = identifier

    override fun getPlaceholderDescription(): LinkedHashMap<String, String> = placeholderDescription

    override fun onRequest(player: Player?, valueIdentifier: String): String? = onRequestFunc(player, valueIdentifier)


}