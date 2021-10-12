package com.mineinabyss.geary.minecraft.actions

import com.mineinabyss.geary.ecs.api.actions.GearyAction
import com.mineinabyss.geary.ecs.api.entities.GearyEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bukkit.entity.Damageable
import org.bukkit.entity.Entity

/**
 * Self destructs (makes an explosion and kills) an entity
 *
 * @param explosionPower Defines the power of the explosion.
 * @param setFire If the surrounding blocks should be set on fire.
 */
@Serializable
@SerialName("geary:selfdestruct")
class SelfDestructAction(
    private val explosionPower: Double = 1.0,
    private val setFire: Boolean
) : GearyAction() {
    val GearyEntity.entity by get<Entity>()

    override fun GearyEntity.run(): Boolean {
        entity.world.createExplosion(entity.location, explosionPower.toFloat(), setFire)
        if (entity is Damageable)
            (entity as Damageable).health = 0.0
        return true
    }
}