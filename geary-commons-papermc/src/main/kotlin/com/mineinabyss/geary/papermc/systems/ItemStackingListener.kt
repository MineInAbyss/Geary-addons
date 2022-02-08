package com.mineinabyss.geary.papermc.systems

import com.mineinabyss.geary.minecraft.components.Stackable
import com.mineinabyss.geary.papermc.access.toGearyOrNull
import com.mineinabyss.geary.papermc.gearyCommonsPlugin
import com.mineinabyss.geary.prefabs.helpers.prefabs
import com.mineinabyss.looty.ecs.components.PlayerInstancedItem
import com.mineinabyss.looty.tracking.toGearyOrNull
import com.okkero.skedule.schedule
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityPickupItemEvent
import org.bukkit.event.inventory.InventoryAction
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent

object ItemStackingListener : Listener {

    @EventHandler
    fun InventoryClickEvent.onCombineItems() {
        val player = inventory.holder as? Player ?: return
        val currItem = currentItem
        val cursor = cursor!!
        val gearyCursor = cursor.toGearyOrNull(player)
        val gearyCurrentItem = currItem?.toGearyOrNull(player)
        val stackable = gearyCurrentItem?.get<Stackable>()
        val cursorStacksize = gearyCursor?.get<Stackable>()?.stackSize

        if (gearyCurrentItem?.has<Stackable>() != true && gearyCursor?.has<Stackable>() != true) return

        isCancelled = true

        if (gearyCurrentItem?.prefabs != gearyCursor?.prefabs) {
            if (currentItem?.type == Material.AIR && cursor.type == Material.AIR) return
            else {
                currentItem = cursor.clone()
                player.setItemOnCursor(currItem)
                return
            }
        }

        when {
            action == InventoryAction.COLLECT_TO_CURSOR -> {
                player.inventory.contents.forEach {
                    if (it == null) return@forEach
                    val gearyIt = it.toGearyOrNull(player) ?: return@forEach
                    if (gearyIt.prefabs != gearyCursor?.prefabs) return@forEach
                    while (cursor.amount < gearyCursor.get<Stackable>()?.stackSize!! && it.amount > 0 && it.amount < cursorStacksize!!) {
                        cursor.amount += 1
                        it.subtract()
                    }
                    return@forEach
                }
            }
            isLeftClick && !isShiftClick -> {
                if (currentItem == null) return
                if (cursor.type == Material.AIR) {
                    view.cursor = currentItem?.clone()
                    currentItem = null
                    return
                }
                if (currentItem?.type == Material.AIR) {
                    currentItem = cursor.clone()
                    view.cursor = null
                    return
                }
                while (currItem?.amount!! < stackable!!.stackSize && view.cursor?.amount!! > 0) {
                    currItem.amount += 1
                    view.cursor?.subtract()
                }
            }
            isRightClick && !isShiftClick -> {
                if (currentItem == null) return
                if (cursor.type == Material.AIR) {
                    val clone = currentItem?.clone()
                    currentItem?.apply { amount /= 2 } // Takes the bigger half on odd-numbers like vanilla
                    view.cursor = clone?.apply { amount -= currentItem?.amount!! }
                    return
                }
                if (currentItem?.type == Material.AIR) {
                    currentItem = cursor.clone()
                    currentItem?.amount = 1
                    view.cursor?.subtract()
                    return
                }
                if (currItem?.amount!! < stackable?.stackSize!!) {
                    currItem.amount += 1
                    view.cursor?.subtract()
                }
            }
            action == InventoryAction.MOVE_TO_OTHER_INVENTORY || isShiftClick -> {
                val gearyCurrent = currItem?.toGearyOrNull(player) ?: return
                if (currentItem == null) return

                player.inventory.contents.forEach {
                    if (it == null || it.type == Material.AIR) return@forEach

                    val invItem = it.toGearyOrNull(player) ?: return@forEach
                    val invItemStack = invItem.get<Stackable>()?.stackSize ?: return@forEach

                    if (invItem.prefabs != gearyCurrent.prefabs) return@forEach
                    gearyCommonsPlugin.schedule {
                        while ((currItem.amount > 0) && (it.amount < invItemStack)) {
                            currItem.amount -= 1
                            it.amount += 1
                            waitFor(1)
                        }
                    }
                    if (it.amount == invItemStack && currItem.amount > 0) return@forEach
                }
            }
            else -> isCancelled = false
        }
    }

    @EventHandler
    fun InventoryCloseEvent.onPlayerClose() {
        player.itemOnCursor.toGearyOrNull(player as Player)?.get<Stackable>() ?: return
        player.inventory.contents.forEach {
            if (it == null) return
            if (player.itemOnCursor.toGearyOrNull(player as Player)?.has<PlayerInstancedItem>() == true) {
                player.world.dropItem(player.location, player.itemOnCursor)
                return
            }
        }
        player.world.dropItem(player.location, player.itemOnCursor)
    }

    @EventHandler
    fun EntityPickupItemEvent.onPickupStackable() {
        val stackSize = item.toGearyOrNull()?.get<Stackable>()?.stackSize ?: return
        if (item.toGearyOrNull()!!.has<PlayerInstancedItem>() && item.itemStack.amount > stackSize) return
    }
}