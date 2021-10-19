package com.mineinabyss.geary.minecraft.actions

import com.mineinabyss.geary.ecs.api.actions.GearyAction
import com.mineinabyss.geary.ecs.api.entities.GearyEntity
import com.mineinabyss.geary.minecraft.gearyCommonsPlugin
import com.okkero.skedule.schedule
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bukkit.entity.Entity

/**
 * Strikes an entity with lightning bolts
 */
@Serializable
@SerialName("geary:smite")
class SmiteEntityAction(
    private val strikeCount: Int,
    private val timeBetween: Double
) : GearyAction() {
    val GearyEntity.entity by get<Entity>()

    override fun GearyEntity.run(): Boolean {
        gearyCommonsPlugin.schedule {
            for (i in 0..strikeCount) {
                entity.world.strikeLightning(entity.location)
                waitFor((timeBetween * 20) as Long)
            }
        }
        return true
    }
}
