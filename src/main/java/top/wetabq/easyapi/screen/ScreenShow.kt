package top.wetabq.easyapi.screen

import cn.nukkit.Player

data class ScreenShow (
    val targetPlayers: Collection<Player>,
    val showMessage: String,
    val showPriority: Int,
    val durationTime: Int, //If durationTime is -1 means always send.
    val afterOccupyDurationTime: Int, // If the player tip be occupy, each single tip hang duration time (ms)
    val exclusive: Boolean = false, // If not exclusive, EasyAPI try to combine two or more tips into a one tip.
    val alternate: Boolean = true, // not support
    val showType: ShowType
) {
    var sendTime = -1L
    var lastAlternativeSendTime = -1L
}