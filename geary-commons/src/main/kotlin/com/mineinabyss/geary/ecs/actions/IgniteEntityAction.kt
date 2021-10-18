package com.derongan.minecraft.mineinabyss.ecs.actions

import com.mineinabyss.geary.ecs.api.actions.GearyAction
import com.mineinabyss.geary.ecs.api.entities.GearyEntity
import com.mineinabyss.idofront.time.TimeSpan
import org.bukkit.entity.Entity

/**
 * Sets an entity on fire for a number of seconds.
 */
class IgniteEntityAction(
    private val length: TimeSpan = TimeSpan(7)
) : GearyAction() {
    val GearyEntity.entity by get<Entity>()

    override fun GearyEntity.run(): Boolean {
        entity.fireTicks = length.inTicks as Int
        return true
    }
}