package com.mineinabyss.geary.minecraft.events

import com.mineinabyss.geary.minecraft.access.toGeary
import com.mineinabyss.idofront.entities.leftClicked
import com.mineinabyss.idofront.entities.rightClicked
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerItemBreakEvent
import org.bukkit.event.player.PlayerItemConsumeEvent

object ItemActionsListener : Listener {
    @EventHandler
    fun PlayerInteractEvent.onClick() {
        player.heldLootyItem?.callEvent(ItemInteraction(leftClicked, rightClicked), this)
    }

    @EventHandler(ignoreCancelled = true)
    fun PlayerItemBreakEvent.onItemBreak() {
        player.heldLootyItem?.callEvent(ItemBreak(), this)
    }

    //TODO dropping items reloads them in the tracking system even if cancelled
    @EventHandler(ignoreCancelled = true)
    fun PlayerDropItemEvent.onItemDrop() {
        player.heldLootyItem?.callEvent(ItemDropped(), this)
    }

    @EventHandler(ignoreCancelled = true)
    fun EntityDamageByEntityEvent.onHit() {
        val player = damager as? Player ?: return

        player.heldLootyItem?.callEvent(
            ItemInteraction(leftClick = true, rightClick = false),
            ItemHitEntity(entity.toGeary()),
            this
        )
    }

    @EventHandler(ignoreCancelled = true)
    fun PlayerItemConsumeEvent.onConsume() {
        player.heldLootyItem?.callEvent(ItemConsumed(), this)
    }
}
