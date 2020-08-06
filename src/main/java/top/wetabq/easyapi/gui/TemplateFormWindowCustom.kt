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