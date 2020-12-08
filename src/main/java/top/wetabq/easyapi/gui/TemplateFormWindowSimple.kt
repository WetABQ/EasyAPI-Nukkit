/*
 * Copyright (c) 2020 WetABQ and contributors
 *
 *  此源代码的使用受 GNU GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU GPLv3 license that can be found through the following link.
 *
 * https://github.com/WetABQ/EasyAPI-Nukkit/blob/master/LICENSE
 */

package top.wetabq.easyapi.gui

import cn.nukkit.Player
import moe.him188.gui.window.ResponsibleFormWindowSimple
import top.wetabq.easyapi.utils.color

class TemplateFormWindowSimple(guiTitle: String = "",
                               guiContent: String = "",
                               init: ((ResponsibleFormWindowSimple) -> Unit)? = null,
                               private val onClose: ((ResponsibleFormWindowSimple, Player) -> Unit)? = null,
                               private val goBack: Boolean = false) : ResponsibleFormWindowSimple(
    guiTitle.color(),
    guiContent.color()
) {

    init {
        init?.invoke(this)
    }

    override fun onClosed(player: Player) {
        onClose?.invoke(this, player)
        if (goBack) goBack(player)
    }

}