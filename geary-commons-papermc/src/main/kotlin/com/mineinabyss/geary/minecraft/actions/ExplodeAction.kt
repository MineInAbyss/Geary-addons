package com.mineinabyss.geary.minecraft.actions

import com.mineinabyss.geary.ecs.accessors.EventResultScope
import com.mineinabyss.geary.ecs.accessors.ResultScope
import com.mineinabyss.geary.ecs.api.autoscan.AutoScan
import com.mineinabyss.geary.ecs.api.systems.GearyListener
import com.mineinabyss.geary.ecs.events.handlers.GearyHandler
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
    private val ResultScope.explosion by get<Explosion>()

    private inner class OnExplosion : GearyHandler() {
        private val EventResultScope.location by get<Location>()

        init {
            has<Explode>()
        }

        override fun ResultScope.handle(event: EventResultScope) {
            if (explosion.fuseTicks <= 0)
                event.location.createExplosion(explosion.power, explosion.setFire, explosion.breakBlocks)
            else //only spawn a tnt in if we have a fuse
                event.location.spawn<TNTPrimed> { fuseTicks = explosion.fuseTicks }
        }
    }
}
