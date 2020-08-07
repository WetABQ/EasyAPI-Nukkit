/*
 * Copyright (c) 2020 WetABQ and contributors
 *
 *  此源代码的使用受 GNU GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU GPLv3 license that can be found through the following link.
 *
 * https://github.com/WetABQ/EasyAPI-Nukkit/blob/master/LICENSE
 */

package top.wetabq.easyapi.particle

import cn.nukkit.Player
import cn.nukkit.level.Position
import cn.nukkit.level.particle.Particle
import kotlin.math.max
import kotlin.math.min

infix fun ClosedRange<Double>.step(step: Double): Iterable<Double> {
    require(start.isFinite())
    require(endInclusive.isFinite())
    require(step > 0.0) { "Step must be positive, was: $step." }
    val sequence = generateSequence(start) { previous ->
        if (previous == Double.POSITIVE_INFINITY) return@generateSequence null
        val next = previous + step
        if (next > endInclusive) null else next
    }
    return sequence.asIterable()
}

object ParticleUtils {


    fun rectangle(pos1: Position, pos2: Position, density: Double, particle: Particle, playerCollection: Collection<Player> = pos1.level.players.values) {
        require(pos1.level != null) { "pos1.level can't be null!" }

        for (x in min(pos1.x, pos2.x)..max(pos1.x, pos2.x) step density) {
            for (y in min(pos1.y, pos2.y)..max(pos1.y, pos2.y) step density) {
                for (z in min(pos1.z, pos2.z)..max(pos1.z, pos2.z) step density) {
                    pos1.level.addParticle(particle.let { it.x = x; it.y = y; it.z = z; it }, playerCollection)
                }
            }
        }

    }


}