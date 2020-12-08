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
import cn.nukkit.form.response.FormResponseCustom
import moe.him188.gui.window.ResponsibleFormWindowCustom
import top.wetabq.easyapi.utils.color

class TemplateFormWindowCustom(guiTitle: String = "",
                               init: ((ResponsibleFormWindowCustom) -> Unit)? = null,
                               private val onClick: ((ResponsibleFormWindowCustom, FormResponseCustom, Player) -> Unit)? = null,
                               private val onClose: ((ResponsibleFormWindowCustom, Player) -> Unit)? = null,
                               private val goBack: Boolean = false) : ResponsibleFormWindowCustom(guiTitle.color()) {

    init {
        init?.invoke(this)
    }

    override fun onClicked(response: FormResponseCustom, player: Player) {
        onClick?.invoke(this, response, player)
    }

    override fun onClosed(player: Player) {
        onClose?.invoke(this, player)
        if (goBack) goBack(player)
    }

}