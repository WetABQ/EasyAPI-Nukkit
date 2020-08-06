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