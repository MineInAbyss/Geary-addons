package com.mineinabyss.geary.minecraft.actions

import com.mineinabyss.geary.ecs.api.entities.GearyEntity
import com.mineinabyss.idofront.time.TimeSpan
import com.mineinabyss.idofront.typealiases.BukkitEntity

/**
 * Sets an entity on fire for a number of seconds.
 */
fun GearyEntity.ignite(length: TimeSpan, entity: BukkitEntity? = get()): Boolean {
    entity ?: return false
    entity.fireTicks = length.inTicks.toInt()
    return true
}
