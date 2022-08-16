package com.mineinabyss.geary.papermc.systems

import com.mineinabyss.geary.papermc.components.Food
import com.mineinabyss.looty.tracking.toGearyOrNull
import org.bukkit.GameMode
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerItemConsumeEvent
import org.bukkit.inventory.EquipmentSlot
import kotlin.random.Random

class CustomFoodSystem : Listener {

    @EventHandler
    fun PlayerItemConsumeEvent.onConsumeFood() {
        val inv = player.inventory
        val hand = when {
            inv.itemInMainHand.isSimilar(item) -> EquipmentSlot.HAND
            inv.itemInOffHand.isSimilar(item) -> EquipmentSlot.OFF_HAND
            else -> return
        }
        val item = if (hand == EquipmentSlot.HAND) inv.itemInMainHand else inv.itemInOffHand
        val gearyFood = item.toGearyOrNull(player)?.get<Food>() ?: return

        isCancelled = true // Cancel vanilla behaviour

        if (player.gameMode != GameMode.CREATIVE) {
            if (gearyFood.replacement != null)
                if (hand == EquipmentSlot.HAND) inv.setItemInMainHand(gearyFood.replacement.toItemStack())
                else inv.setItemInOffHand(gearyFood.replacement.toItemStack())
            else item.subtract()

            if (gearyFood.effectList.isNotEmpty() && Random.nextDouble(0.0, 1.0) <= gearyFood.effectChance)
                gearyFood.effectList.forEach { effect -> player.addPotionEffect(effect) }

            player.foodLevel += minOf(gearyFood.hunger, 20)
            player.saturation += minOf(gearyFood.saturation, 20)
        }
    }
}
