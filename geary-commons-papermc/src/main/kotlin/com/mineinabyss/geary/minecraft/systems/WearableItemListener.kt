package com.mineinabyss.geary.minecraft.systems

import com.mineinabyss.geary.minecraft.components.Hat
import com.mineinabyss.geary.minecraft.hasComponentsEncoded
import com.mineinabyss.idofront.entities.rightClicked
import com.mineinabyss.looty.tracking.toGearyOrNull
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.EquipmentSlot

object WearableItemListener : Listener {
    @EventHandler
    fun InventoryClickEvent.shiftClickToWear() {
        if (!click.isShiftClick) return
        val player = inventory.holder as? Player ?: return
        val currItem = currentItem ?: return

        val entity = currItem.toGearyOrNull(player)
        entity?.get<Hat>() ?: return

        if (player.inventory.helmet == null) {
            player.inventory.helmet = currItem
            currentItem = null
            isCancelled = true
        }
    }

    @EventHandler
    fun InventoryClickEvent.clickToWear() {
        if (slotType !== InventoryType.SlotType.ARMOR) return
        if (rawSlot != 5) return
        val player = inventory.holder as? Player ?: return

        val cursor = cursor ?: return

        if (cursor.itemMeta?.persistentDataContainer?.hasComponentsEncoded == false) return

        val entity = cursor.toGearyOrNull(player)
        entity?.get<Hat>() ?: return

        val currItem = currentItem?.clone() ?: return // item will not be null, it will be air

        // swap the items from cursor to helmet slot
        currentItem = cursor.clone()
        view.cursor = currItem
        isCancelled = true
    }

    @EventHandler
    fun PlayerInteractEvent.rightClickToWear() {
        if (hand == EquipmentSlot.OFF_HAND) return //the event is called twice, on for each hand. We want to ignore the offhand call
        if (!rightClicked) return //only do stuff when player rightclicks
        if (player.inventory.helmet !== null) return // don't equip if we are wearing a helmet

        val currItem = player.inventory.itemInMainHand
        if (currItem.itemMeta?.persistentDataContainer?.hasComponentsEncoded == false) return

        val entity = currItem.toGearyOrNull(player)
        val hat = entity?.get<Hat>() ?: return // item is not a hat

        player.inventory.helmet = currItem.clone()
        player.playSound(player.location, hat.sound, 1f, 1f)
        currItem.subtract(1)
    }

}
