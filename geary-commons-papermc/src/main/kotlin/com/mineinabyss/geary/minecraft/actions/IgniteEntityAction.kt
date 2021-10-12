package com.mineinabyss.geary.minecraft.actions

import com.mineinabyss.geary.ecs.api.actions.GearyAction
import com.mineinabyss.geary.ecs.api.entities.GearyEntity
import com.mineinabyss.idofront.time.TimeSpan
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bukkit.entity.Entity

/**
 * Ignites the targeted entity.
 *
 * @param length The time ignition will last.
 */
@Serializable
@SerialName("geary:ignite_entity")
class IgniteEntityAction(
    private val length: TimeSpan
) : GearyAction() {
    val GearyEntity.entity by get<Entity>()

    override fun GearyEntity.run(): Boolean {
        entity.fireTicks = length.inTicks.toInt()
        return true
    }
}