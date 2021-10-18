package com.derongan.minecraft.mineinabyss.ecs.actions

import com.derongan.minecraft.mineinabyss.mineInAbyss
import com.mineinabyss.geary.ecs.api.actions.GearyAction
import com.mineinabyss.geary.ecs.api.entities.GearyEntity
import com.mineinabyss.idofront.time.TimeSpan
import com.okkero.skedule.schedule
import org.bukkit.entity.Entity
import org.bukkit.util.Vector

/**
 * Freezes an entity in place in the world for the given duration.
 */
class FreezeEntityAction (
    private val length: TimeSpan = TimeSpan(7)
) : GearyAction() {
    val GearyEntity.entity by get<Entity>()

    override fun GearyEntity.run(): Boolean {
        val initialLocation = entity.location
        var timePassed = 0L
        var lastTime = System.currentTimeMillis()
        mineInAbyss.schedule {
            entity.teleport(initialLocation)
            entity.velocity = Vector(0, 0, 0)
            if(timePassed < length.inTicks && entity.fireTicks == 0) {
                // There's definitely a better way to do this but this should
                // probably work for now
                timePassed += System.currentTimeMillis() - lastTime
                lastTime = System.currentTimeMillis()
                waitFor(1)
            }
        }
        return true
    }
}