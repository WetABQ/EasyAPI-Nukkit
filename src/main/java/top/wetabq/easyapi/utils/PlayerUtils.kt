package top.wetabq.easyapi.utils

import cn.nukkit.Player

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