package com.mineinabyss.geary.minecraft.events.bridge

import com.mineinabyss.geary.ecs.api.entities.GearyEntity
import com.mineinabyss.geary.minecraft.access.toGeary
import com.mineinabyss.geary.minecraft.events.bridge.components.*
import com.mineinabyss.geary.minecraft.systems.exposure.components.Ingested
import com.mineinabyss.geary.minecraft.systems.exposure.components.Touched
import com.mineinabyss.idofront.entities.leftClicked
import com.mineinabyss.idofront.entities.rightClicked
import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerItemBreakEvent
import org.bukkit.event.player.PlayerItemConsumeEvent

fun GearyEntity.setBukkitEvent(event: Event) {
    // Set both the direct class and generic Event
    set(event, event::class)
    set(event)
}

object ItemActionsListener : Listener {
    @EventHandler
    fun PlayerInteractEvent.onClick() {
        player.heldLootyItem?.callEvent(source = player.toGeary()) {
            set(Interacted(leftClicked, rightClicked))
            if (leftClicked) set(LeftClicked())
            if (rightClicked) set(RightClicked())
            setBukkitEvent(this@onClick)
        }
    }

    @EventHandler(ignoreCancelled = true)
    fun PlayerItemBreakEvent.onItemBreak() {
        player.heldLootyItem?.callEvent {
            set(ItemBroke())
            setBukkitEvent(this@onItemBreak)
        }
    }

    //TODO dropping items reloads them in the tracking system even if cancelled
    @EventHandler(ignoreCancelled = true)
    fun PlayerDropItemEvent.onItemDrop() {
        player.heldLootyItem?.callEvent(source = player.toGeary()) {
            set(ItemDropped())
            setBukkitEvent(this@onItemDrop)
        }
    }

    @EventHandler(ignoreCancelled = true)
    fun EntityDamageByEntityEvent.onHit() {
        val player = damager as? Player ?: return

        //TODO perhaps an event that triggers item -> player, then player -> target
        entity.toGeary().callEvent(source = player.heldLootyItem) {
            set(Interacted(leftClick = true, rightClick = false))
            set(Attacked())
            setBukkitEvent(this@onHit)
        }
    }

    @EventHandler(ignoreCancelled = true)
    fun PlayerItemConsumeEvent.onConsume() {
        player.toGeary().callEvent(source = player.heldLootyItem) {
            setAll(setOf(Ingested(), Touched()))
            setBukkitEvent(this@onConsume)
        }
    }
}
