package top.wetabq.easyapi.placeholder

import cn.nukkit.Player
import top.wetabq.easyapi.module.IEasyAPIModule

class SimplePlaceholder(
    private val owner: IEasyAPIModule,
    private val identifier: String,
    private val onRequestFunc: (Player?, String) -> String?,
    private val placeholderDescription: Map<String, String> = hashMapOf(),
    private val canRegisterFunc: () -> Boolean = {true}
): AbstractPlaceholderExpansion() {

    override fun canRegister(): Boolean = canRegisterFunc()

    override fun getOwner(): IEasyAPIModule = owner

    override fun getIdentifier(): String = identifier

    override fun getPlaceholderDescription(): Map<String, String> = placeholderDescription

    override fun onRequest(player: Player?, valueIdentifier: String): String? = onRequestFunc(player, valueIdentifier)


}