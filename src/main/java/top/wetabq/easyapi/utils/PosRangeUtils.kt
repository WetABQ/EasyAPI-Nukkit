/*
 * Copyright (c) 2020 WetABQ and contributors
 *
 *  此源代码的使用受 GNU GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU GPLv3 license that can be found through the following link.
 *
 * https://github.com/WetABQ/EasyAPI-Nukkit/blob/master/LICENSE
 */

package top.wetabq.easyapi.utils

import cn.nukkit.math.Vector3
import kotlin.math.max
import kotlin.math.min

fun Double.isBetween(double1: Double, double2: Double): Boolean = this in min(double1, double2)..max(double1, double2)

fun Vector3.isInRange(vector3: Vector3, range: Int): Boolean = isInRange(this.x, this.z, vector3.x, vector3.z, range)

fun isInRange(x1: Double, z1: Double, x2: Double, z2: Double, range: Int): Boolean =
    x1.isBetween(x2 + range, x2 - range) && z1.isBetween(z2 + range, z2 - range)