/*
 * Copyright (c) 2020 WetABQ and contributors
 *
 *  此源代码的使用受 GNU GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU GPLv3 license that can be found through the following link.
 *
 * https://github.com/WetABQ/EasyAPI-Nukkit/blob/master/LICENSE
 */

package top.wetabq.easyapi.screen

import cn.nukkit.Player
import top.wetabq.easyapi.module.defaults.ScreenShowModule

// ScreenShowBuilder -> ScreenShow -> ScreenShowModule - send queue
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

    fun addToQueue() = ScreenShowModule.addScreenShow(this)

}