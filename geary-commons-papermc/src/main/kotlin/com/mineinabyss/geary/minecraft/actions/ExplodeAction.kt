package com.mineinabyss.geary.minecraft.actions

import com.mineinabyss.geary.ecs.accessors.ResultScope
import com.mineinabyss.geary.ecs.api.systems.GearyHandlerScope
import com.mineinabyss.geary.ecs.api.systems.GearyListener
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

class ExplosionHandler(): GearyListener() {
    private val ResultScope.explosion by get<Explosion>()
    private val ResultScope.location by get<Location>()

    override fun GearyHandlerScope.register() {
        on<Explosion> {
            if (explosion.fuseTicks <= 0)
                location.createExplosion(explosion.power, explosion.setFire, explosion.breakBlocks)
            else //only spawn a tnt in if we have a fuse
                location.spawn<TNTPrimed> { fuseTicks = explosion.fuseTicks }
        }
    }
}
