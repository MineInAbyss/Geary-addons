package com.derongan.minecraft.mineinabyss.ecs.actions

import com.derongan.minecraft.mineinabyss.mineInAbyss
import com.mineinabyss.geary.ecs.api.actions.GearyAction
import com.mineinabyss.geary.ecs.api.entities.GearyEntity
import com.mineinabyss.idofront.time.TimeSpan
import com.okkero.skedule.schedule
import org.bukkit.entity.Entity

/**
 * Applies the max freezing effect (from being in powdered snow) to an entity
 * for a number of seconds.
 */
class ChillEntityAction (
    private val length: TimeSpan = TimeSpan(7)
) : GearyAction() {

    val GearyEntity.entity by get<Entity>()

    override fun GearyEntity.run(): Boolean {
        var timePassed = 0L
        var lastTime = System.currentTimeMillis()
        mineInAbyss.schedule {
            entity.freezeTicks = entity.maxFreezeTicks
            if(timePassed < length.inTicks && entity.fireTicks == 0) {
                // There's definitely a better way to do this but this should
                // probably work for now
                timePassed += System.currentTimeMillis() - lastTime
                waitFor(1)
            }
        }
        return true
    }
}