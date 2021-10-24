package com.mineinabyss.geary.minecraft.systems

import com.mineinabyss.geary.ecs.api.entities.with
import com.mineinabyss.geary.ecs.api.systems.TickingSystem
import com.mineinabyss.geary.ecs.engine.iteration.QueryResult
import com.mineinabyss.geary.ecs.entities.parent
import com.mineinabyss.geary.minecraft.components.DenyStacking
import com.mineinabyss.geary.minecraft.components.Stackable
import com.mineinabyss.idofront.messaging.broadcast
import com.mineinabyss.idofront.messaging.broadcastVal
import com.mineinabyss.looty.tracking.toGearyOrNull
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

object ItemStackingSystem : TickingSystem(), Listener {
    private val QueryResult.stackable by get<Stackable>()
    private val QueryResult.denyStacking by get<DenyStacking>()
    private val QueryResult.item by get<ItemStack>()

    override fun QueryResult.tick() {
        entity.parent?.with { player: Player ->
            val gearyItem = item.toGearyOrNull(player) ?: return
            gearyItem.get<DenyStacking>() ?: return
            gearyItem.remove<DenyStacking>()
            gearyItem.get<DenyStacking>().broadcastVal("denystacking: ")
        }
    }

    @EventHandler
    fun InventoryClickEvent.onStackingItems() {
        if (!click.isLeftClick) return

        val player = inventory.holder as? Player ?: return
        val cursorClone = cursor?.clone() ?: return
        val gearyCursor = cursor?.toGearyOrNull(player)
        val gearyCurrentItem = currentItem?.toGearyOrNull(player)
        val newSize: Int? = cursor?.amount?.plus(currentItem!!.amount)
        val newStack = gearyCurrentItem?.get<Stackable>() ?: return
        gearyCursor?.get<Stackable>() ?: return


        if (newSize!! <= newStack.stackSize) {
            cursor?.amount.broadcastVal("cursor1: ")
            //currentItem!!.amount -= 1
            //cursor!!.amount += 1
            broadcast("allowed")
            newSize.broadcastVal("newsize: ")
            isCancelled = true
            view.cursor = null
            //currentItem = clone
            while (currentItem!!.amount < newStack.stackSize && cursor?.amount != 0) {
                currentItem!!.amount += 1
                cursor?.amount = cursor?.amount?.minus(1)!!
            }
            //cursor = null

            currentItem?.amount.broadcastVal("curr1: ")
            return
        }
//        if (newSize > cursor?.amount!!){
//            while (currentItem!!.amount < newStack.stackSize){
//                currentItem!!.amount += 1
//                cursor?.amount = cursor?.amount?.minus(1)!!
//            }
//        }
        if (currentItem!!.amount == newStack.stackSize) {
            broadcast("ultra deny")
            return
        }
    }
}