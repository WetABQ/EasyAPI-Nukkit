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