package com.mineinabyss.geary.minecraft.actions

import com.mineinabyss.geary.ecs.api.actions.GearyAction
import com.mineinabyss.geary.ecs.api.entities.GearyEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bukkit.entity.Entity


/**
 * Freezes the targeted entity.
 */
@Serializable
@SerialName("geary:freeze_entity")
class FreezeEntityAction() : GearyAction() {
    val GearyEntity.entity by get<Entity>()
    override fun GearyEntity.run(): Boolean {
        entity.freezeTicks = entity.maxFreezeTicks
        return true
    }
}