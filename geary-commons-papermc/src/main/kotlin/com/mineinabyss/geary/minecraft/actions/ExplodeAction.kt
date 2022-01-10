package com.mineinabyss.geary.minecraft.actions

import com.mineinabyss.geary.ecs.accessors.EventScope
import com.mineinabyss.geary.ecs.accessors.TargetScope
import com.mineinabyss.geary.ecs.accessors.get
import com.mineinabyss.geary.ecs.api.autoscan.AutoScan
import com.mineinabyss.geary.ecs.api.systems.GearyListener
import com.mineinabyss.geary.ecs.api.systems.Handler
import com.mineinabyss.idofront.spawning.spawn
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bukkit.Location
import org.bukkit.entity.TNTPrimed

@Serializable
@SerialName("geary:explosion")
data class Explosion(
    val power: Float = 4F,
    val setFire: Boolean = false,
    val breakBlocks: Boolean = false,
    val fuseTicks: Int = 0
)

class Explode

@AutoScan
class ExplosionSystem() : GearyListener() {
    private val TargetScope.explosion by get<Explosion>()

    private val EventScope.location by get<Location>()

    init {
        event.has<Explode>()
    }

    @Handler
    fun onExplode(target: TargetScope, event: EventScope) {
        if (target.explosion.fuseTicks <= 0)
            event.location.createExplosion(
                target.explosion.power,
                target.explosion.setFire,
                target.explosion.breakBlocks
            )
        else //only spawn a tnt in if we have a fuse
            event.location.spawn<TNTPrimed> { fuseTicks = target.explosion.fuseTicks }
    }
}
