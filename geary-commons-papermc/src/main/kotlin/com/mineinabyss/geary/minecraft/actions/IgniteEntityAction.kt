package com.mineinabyss.geary.minecraft.actions

import com.mineinabyss.geary.ecs.api.actions.GearyAction
import com.mineinabyss.geary.ecs.api.entities.GearyEntity
import org.bukkit.entity.Entity

class IgniteEntityAction(
    private val seconds: Int = 7
) : GearyAction() {
    val GearyEntity.entity by get<Entity>()

    override fun GearyEntity.run(): Boolean {
        entity.fireTicks = seconds * 20  // At 20 ticks / second
        return true
    }
}