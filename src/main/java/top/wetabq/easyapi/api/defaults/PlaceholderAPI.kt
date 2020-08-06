package top.wetabq.easyapi.api.defaults

import cn.nukkit.Player
import top.wetabq.easyapi.api.SimpleIntegrateAPI
import top.wetabq.easyapi.module.defaults.PlaceholderManager

object PlaceholderAPI: SimpleIntegrateAPI {

    fun setPlaceholder(player: Player?, content: String): String = PlaceholderManager.applyPlaceholder(player, content)

    fun setPlaceholder(content: String): String = setPlaceholder(null, content)

}