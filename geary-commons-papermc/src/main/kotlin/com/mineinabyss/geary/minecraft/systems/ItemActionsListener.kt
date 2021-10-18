package com.mineinabyss.geary.minecraft.systems

import com.mineinabyss.geary.ecs.components.Target
import com.mineinabyss.geary.minecraft.access.toGeary
import com.mineinabyss.geary.minecraft.components.event
import com.mineinabyss.idofront.entities.leftClicked
import com.mineinabyss.idofront.entities.rightClicked
import com.mineinabyss.looty.tracking.toGearyOrNull
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerItemBreakEvent
import org.bukkit.event.player.PlayerItemConsumeEvent

val Player.heldLootyItem get() = inventory.itemInMainHand.toGearyOrNull(this)

object ItemActionsListener : Listener {
    @EventHandler
    fun PlayerInteractEvent.onClick() {
        if (leftClicked) event(player.heldLootyItem, "leftClick")
        if (rightClicked) event(player.heldLootyItem, "rightClick")
    }

    @EventHandler(ignoreCancelled = true)
    fun PlayerItemBreakEvent.onItemBreak() {
        event(player.heldLootyItem, "break")
    }

    //TODO dropping items reloads them in the tracking system even if cancelled
    @EventHandler(ignoreCancelled = true)
    fun PlayerDropItemEvent.onItemDrop() {
        event(player.heldLootyItem, "drop")
    }

    //TODO some of these will get repetitive between items and mobs, consider sharing code somehow
    @EventHandler(ignoreCancelled = true)
    fun EntityDamageByEntityEvent.onHit() {
        val player = damager as? Player ?: return
        val gearyEntity = player.heldLootyItem ?: return

        gearyEntity.set(Target(entity.toGeary()))
        event(gearyEntity, "hitEntity")
        gearyEntity.remove<Target>()
    }

    @EventHandler(ignoreCancelled = true)
    fun PlayerItemConsumeEvent.onConsume() {
        event(player.heldLootyItem, "consume")
    }
}