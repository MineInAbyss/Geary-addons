package com.mineinabyss.geary.papermc.systems.bridge

import com.mineinabyss.geary.commons.components.interaction.*
import com.mineinabyss.geary.papermc.access.toGeary
import com.mineinabyss.geary.papermc.helpers.heldLootyItem
import com.mineinabyss.geary.papermc.helpers.setBukkitEvent
import com.mineinabyss.idofront.entities.leftClicked
import com.mineinabyss.idofront.entities.rightClicked
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerItemBreakEvent
import org.bukkit.event.player.PlayerItemConsumeEvent

class ItemActionsBridge : Listener {
    private val rightClickCooldowns = Int2IntOpenHashMap()

    @EventHandler
    fun PlayerInteractEvent.onClick() {
        player.heldLootyItem?.callEvent(source = player.toGeary()) {
            set(Interacted(leftClicked, rightClicked))
            if (leftClicked) set(LeftClicked())

            // Right click gets fired twice, so we manually prevent two right-clicks within several ticks of each other.
            val currTick = Bukkit.getServer().currentTick
            val eId = player.entityId
            if (rightClicked && currTick - rightClickCooldowns[eId] > 3) {
                rightClickCooldowns[eId] = currTick
                set(RightClicked())
            }
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
