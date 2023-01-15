package com.mineinabyss.geary.papermc.systems

import com.mineinabyss.geary.papermc.components.Food
import com.mineinabyss.looty.tracking.toGearyOrNull
import org.bukkit.GameMode
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerItemConsumeEvent
import kotlin.random.Random

class CustomFoodSystem : Listener {

    @EventHandler
    fun PlayerItemConsumeEvent.onConsumeFood() {
        val inv = player.inventory
        val item = if (inv.itemInMainHand.isSimilar(item)) inv.itemInMainHand else inv.itemInOffHand
        val gearyFood = item.toGearyOrNull(player)?.get<Food>() ?: return
        val replacement = gearyFood.replacement?.toItemStack()
        isCancelled = true // Cancel vanilla behaviour

        if (player.gameMode != GameMode.CREATIVE) {
            if (replacement != null) {
                if (player.inventory.firstEmpty() != -1) inv.addItem(replacement)
                else player.world.dropItemNaturally(player.location, replacement)
            }
            item.subtract()

            if (gearyFood.effectList.isNotEmpty() && Random.nextDouble(0.0, 1.0) <= gearyFood.effectChance)
                player.addPotionEffects(gearyFood.effectList)
        }

        player.foodLevel += minOf(gearyFood.hunger, 20)
        player.saturation += minOf(gearyFood.saturation, 20)
    }
}
