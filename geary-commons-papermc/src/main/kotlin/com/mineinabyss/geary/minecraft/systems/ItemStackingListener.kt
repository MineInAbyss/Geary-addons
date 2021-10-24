package com.mineinabyss.geary.minecraft.systems

import com.mineinabyss.geary.ecs.entities.prefabs
import com.mineinabyss.geary.minecraft.components.Stackable
import com.mineinabyss.idofront.messaging.broadcast
import com.mineinabyss.looty.tracking.toGearyOrNull
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

object ItemStackingListener : Listener {

    @EventHandler(priority = EventPriority.LOW)
    fun InventoryClickEvent.onCombineItems() {
        val player = inventory.holder as? Player ?: return
        val currItem = currentItem
        val cursor = cursor ?: return
        val gearyCursor = cursor.toGearyOrNull(player) ?: return
        val gearyCurrentItem = currItem?.toGearyOrNull(player)
        val stackable = gearyCurrentItem?.get<Stackable>()

        //isCancelled = true

        if (gearyCurrentItem?.prefabs != gearyCursor.prefabs && currentItem!!.type != Material.AIR) return

        if (isLeftClick) {
            if (currentItem?.type == Material.AIR) {
                currentItem = cursor.clone()
                view.cursor = null
                return
            }
            while (currItem!!.amount < stackable!!.stackSize && view.cursor?.amount!! > 0) {
                currItem.amount += 1
                view.cursor?.subtract()
            }
            return
        }

        if (isRightClick) {
            if (currentItem?.type == Material.AIR) {
                currentItem = cursor.clone()
                currentItem?.amount = 1
                view.cursor?.subtract()
            }
            if (currItem!!.amount < stackable!!.stackSize) {
                currItem.amount += 1
                view.cursor?.subtract()
            }
            return
        }

        if (isShiftClick) {
            broadcast("bruh")
            inventory.contents.forEach {
                val item = it.toGearyOrNull(player)
                item?.get<Stackable>() ?: return
                if (currentItem!!.amount + it.amount <= stackable!!.stackSize) {
                    it.amount += currentItem!!.amount
                    currentItem?.amount = 0
                }
                return
            }
        }
    }
}