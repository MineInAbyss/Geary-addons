package com.mineinabyss.geary.minecraft.events

import com.mineinabyss.geary.ecs.api.entities.GearyEntity
import com.mineinabyss.looty.tracking.toGearyOrNull
import org.bukkit.entity.Player

val Player.heldLootyItem get() = inventory.itemInMainHand.toGearyOrNull(this)

data class ItemInteraction(
    val leftClick: Boolean,
    val rightClick: Boolean,
)

data class ItemHitEntity(
    val target: GearyEntity,
)

class ItemBreak()

class ItemDropped()

class ItemConsumed()
