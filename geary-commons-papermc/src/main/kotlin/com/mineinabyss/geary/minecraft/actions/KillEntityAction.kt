package com.mineinabyss.geary.minecraft.actions

import com.mineinabyss.geary.ecs.api.actions.GearyAction
import com.mineinabyss.geary.ecs.api.entities.GearyEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bukkit.entity.Entity

/**
 * Kills an entity
 */
@Serializable
@SerialName("geary:kill")
class KillEntityAction() : GearyAction() {
    val GearyEntity.entity by get<Entity>()

    override fun GearyEntity.run(): Boolean {
        entity.remove()
        return true
    }
}