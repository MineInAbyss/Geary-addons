package com.mineinabyss.geary.papermc.systems

import com.mineinabyss.geary.papermc.components.Food
import com.mineinabyss.looty.tracking.toGearyOrNull
import org.bukkit.GameMode
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerItemConsumeEvent
import org.bukkit.potion.PotionEffect
import kotlin.random.Random

class CustomFoodSystem : Listener {

    @EventHandler
    fun PlayerItemConsumeEvent.onConsumeFood() {
        val gearyFood = item.toGearyOrNull(player)?.get<Food>() ?: return
        isCancelled = true // Cancel vanilla behaviour
        if (player.gameMode == GameMode.CREATIVE) return

        if (gearyFood.replacement != null) setItem(gearyFood.replacement.toItemStack()) else item.subtract()
        if (gearyFood.effectList.isNotEmpty() && gearyFood.effectChance > 0.0) {
            if (Random.nextDouble(0.0, 1.0) <= gearyFood.effectChance)
                gearyFood.effectList.forEach { e ->
                    player.addPotionEffect(PotionEffect(e.type, e.duration, e.amplifier, e.isAmbient, e.hasParticles(), e.hasIcon()))
                }
        }

        player.foodLevel += minOf(gearyFood.hunger, 20)
        player.saturation += minOf(gearyFood.saturation, 20)
    }
}
