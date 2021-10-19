package com.mineinabyss.geary.minecraft.actions

import com.mineinabyss.geary.ecs.api.actions.GearyAction
import com.mineinabyss.geary.ecs.api.entities.GearyEntity
import com.mineinabyss.geary.minecraft.gearyCommonsPlugin
import com.mineinabyss.idofront.time.TimeSpan
import com.okkero.skedule.schedule
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bukkit.entity.Entity

/**
 * Applies the max freezing effect (from being in powdered snow) to an entity
 * for a number of seconds.
 */
@Serializable
@SerialName("geary:chill")
class ChillEntityAction(
    private val length: TimeSpan = TimeSpan(7)
) : GearyAction() {

    val GearyEntity.entity by get<Entity>()

    override fun GearyEntity.run(): Boolean {
        gearyCommonsPlugin.schedule {
            var timePassed = 0L
            val lastTime = System.currentTimeMillis()
            do {
                entity.freezeTicks = entity.maxFreezeTicks
                timePassed += System.currentTimeMillis() - lastTime
                waitFor(1)
            } while (timePassed < length.inTicks)
        }
        return true
    }
}