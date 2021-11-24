package com.mineinabyss.geary.minecraft.events

import com.mineinabyss.geary.ecs.api.entities.GearyEntity
import com.mineinabyss.geary.ecs.api.systems.EventRunner
import com.mineinabyss.geary.ecs.api.systems.GearyHandlerScope
import com.mineinabyss.idofront.entities.leftClicked
import com.mineinabyss.idofront.entities.rightClicked
import com.mineinabyss.looty.tracking.toGearyOrNull
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerItemBreakEvent
import org.bukkit.event.player.PlayerItemConsumeEvent

val Player.heldLootyItem get() = inventory.itemInMainHand.toGearyOrNull(this)

fun GearyHandlerScope.onItemClick(run: EventRunner<PlayerInteractEvent>) = on(run)

fun GearyHandlerScope.onItemLeftClick(run: EventRunner<PlayerInteractEvent>) = on<PlayerInteractEvent> {
    if (it.leftClicked) run(it)
}

fun GearyHandlerScope.onItemRightClick(run: EventRunner<PlayerInteractEvent>) = on<PlayerInteractEvent> {
    if (it.rightClicked) run(it)
}

fun GearyHandlerScope.onItemBreak(run: EventRunner<PlayerItemBreakEvent>) = on(run)

fun GearyHandlerScope.onItemDrop(run: EventRunner<PlayerDropItemEvent>) = on(run)


class ItemHitEntityInteraction(
    val player: GearyEntity,
    val target: GearyEntity,
    val event: EntityDamageByEntityEvent,
)

fun GearyHandlerScope.onItemHitEntity(run: EventRunner<ItemHitEntityInteraction>) = on(run)

fun GearyHandlerScope.onItemConsume(run: EventRunner<PlayerItemConsumeEvent>) = on(run)
