package com.mineinabyss.geary.minecraft.systems

import com.mineinabyss.geary.ecs.entities.prefabs
import com.mineinabyss.geary.minecraft.components.Stackable
import com.mineinabyss.idofront.messaging.broadcast
import com.mineinabyss.idofront.messaging.broadcastVal
import com.mineinabyss.looty.tracking.toGearyOrNull
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryAction
import org.bukkit.event.inventory.InventoryClickEvent

object ItemStackingListener : Listener {

    @EventHandler
    fun InventoryClickEvent.onCombineItems() {
        action.broadcastVal("Action: ")
        val player = inventory.holder as? Player ?: return
        "inv click".broadcastVal()
        val currItem = currentItem
        val cursor = cursor!!
        val gearyCursor = cursor.toGearyOrNull(player)
        "after cursor".broadcastVal()
        val gearyCurrentItem = currItem?.toGearyOrNull(player)
        val stackable = gearyCurrentItem?.get<Stackable>()

        if (gearyCurrentItem?.has<Stackable>() != true && gearyCursor?.has<Stackable>() != true) return

        isCancelled = true

        if (gearyCurrentItem?.prefabs != gearyCursor?.prefabs && currentItem?.type != Material.AIR && cursor.type != Material.AIR) return

        when {
            action == InventoryAction.COLLECT_TO_CURSOR -> {
                player.inventory.contents.forEach {
                    if (it == null) return@forEach
                    val gearyIt = it.toGearyOrNull(player) ?: return@forEach
                    if (gearyIt.prefabs != gearyCursor?.prefabs) return@forEach
                    while (cursor.amount < gearyCursor.get<Stackable>()?.stackSize!! && it.amount > 0) {
                        cursor.amount += 1
                        it.subtract()
                    }
                    return@forEach
                }
            }
            isLeftClick && !isShiftClick -> {
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
                while (currItem!!.amount < stackable!!.stackSize && view.cursor?.amount!! > 0) {
                    currItem.amount += 1
                    view.cursor?.subtract()
                }
            }
            isRightClick && !isShiftClick -> {
                if (cursor.type == Material.AIR) {
                    val clone = currentItem?.clone()
                    currentItem?.apply { amount /= 2 } // Takes the bigger half on odd-numbers like vanilla
                    view.cursor = clone?.apply { amount -= currentItem?.amount!! }
                    return
                }
                if (currentItem?.type == Material.AIR) {
                    currentItem = cursor.clone()
                    currentItem?.amount = 1
                    view.cursor!!.subtract()
                    return
                }
                if (currItem!!.amount < stackable!!.stackSize) {
                    currItem.amount += 1
                    view.cursor?.subtract()
                }
            }

            action == InventoryAction.MOVE_TO_OTHER_INVENTORY || isShiftClick -> {//
                gearyCurrentItem ?: return
                player.inventory.storageContents.forEach {
                    if (it == null) return@forEach // This fails after repeated attempts?
                    val gearyIt = it.toGearyOrNull(player) ?: return@forEach // This fails after repeated attempts?
                    broadcast("cock")
                    val itStackable = gearyIt.get<Stackable>()?.stackSize ?: return
                    val itMaxAdd = currItem.amount.coerceAtMost(itStackable - it.amount)
                    if (gearyIt.prefabs != gearyCurrentItem.prefabs) return@forEach
                    //if (it.amount > gearyIt.get<Stackable>()!!.stackSize || currItem.amount < 0) return
                    broadcast("cock2")
                    currItem.broadcastVal("curr: ")
                    it.broadcastVal("it: ")
                    if (it.amount < itStackable && currItem.amount > 0) {
                        it.amount += itMaxAdd
                        currItem.amount -= itMaxAdd
                    }
                    if (currItem.amount > 0) return@forEach
                }
                return
            }
            else -> isCancelled = false
        }
    }
}