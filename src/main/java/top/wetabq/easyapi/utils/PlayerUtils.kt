/*
 * Copyright (c) 2020 WetABQ and contributors
 *
 *  此源代码的使用受 GNU GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU GPLv3 license that can be found through the following link.
 *
 * https://github.com/WetABQ/EasyAPI-Nukkit/blob/master/LICENSE
 */

package top.wetabq.easyapi.utils

import cn.nukkit.Player
import cn.nukkit.utils.TextFormat

fun Player.getCardinalDirection() : String {
    var rotation = this.location.getYaw() - 180.0f

    if (rotation < 0.0) rotation += 360.0

    return when {
        0.0 <= rotation && rotation < 22.5 ->  "N"
        22.5 <= rotation && rotation < 67.5 -> "NE"
        67.5 <= rotation && rotation < 112.5 -> "E"
        112.5 <= rotation && rotation < 157.5 -> "SE"
        157.5 <= rotation && rotation < 202.5 -> "S"
        202.5 <= rotation && rotation < 247.5 || rotation <= -119.33 && rotation > -179 -> "SW"
        247.5 <= rotation && rotation < 292.5 || rotation <= -59.66 && rotation > -119.33 -> "W"
        292.5 <= rotation && rotation < 337.5 || rotation <= -0.0 && rotation > -59.66 -> "NW"
        337.5 <= rotation && rotation < 360.0 -> "N"
        else -> return "NULL"
    }

}

fun Player.getHealthPopupString(): String {
    val health = kotlin.math.floor(health).toInt()
    val sb = StringBuilder()
    sb.append(TextFormat.RED)
    sb.append(getBasePopupHealth(health))
    val absorption = kotlin.math.floor(absorption).toInt()
    if (absorption > 0) {
        sb.append(TextFormat.YELLOW)
        sb.append(getBasePopupHealth(absorption))
    }
    return sb.toString()
}


private fun getBasePopupHealth(absorption: Int): String? {
    val sb = StringBuilder()
    if (absorption > 0) {
        if (absorption > 1) {
            for (i in 0..absorption / 2) {
                sb.append("♥")
            }
        }
        if (absorption % 2 != 0) {
            sb.append(TextFormat.WHITE)
            sb.append("♥")
        }
    }
    return sb.toString()
}