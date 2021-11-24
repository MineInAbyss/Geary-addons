package com.mineinabyss.geary.minecraft.actions

import com.mineinabyss.geary.ecs.api.entities.GearyEntity
import com.mineinabyss.geary.minecraft.gearyCommonsPlugin
import com.mineinabyss.idofront.time.TimeSpan
import com.mineinabyss.idofront.typealiases.BukkitEntity
import com.okkero.skedule.schedule
import org.bukkit.util.Vector

/**
 * Freezes an entity in place in the world for the given duration.
 */
fun GearyEntity.freeze(
    length: TimeSpan = TimeSpan(7),
    entity: BukkitEntity? = get()
): Boolean {
    entity ?: return false

    val initialLocation = entity.location
    var timePassed = 0L
    var lastTime = System.currentTimeMillis()
    gearyCommonsPlugin.schedule {
        entity.teleport(initialLocation)
        entity.velocity = Vector(0, 0, 0)
        if (timePassed < length.inTicks && entity.fireTicks == 0) {
            // There's definitely a better way to do this but this should
            // probably work for now
            timePassed += System.currentTimeMillis() - lastTime
            lastTime = System.currentTimeMillis()
            waitFor(1)
        }
    }
    return true
}
