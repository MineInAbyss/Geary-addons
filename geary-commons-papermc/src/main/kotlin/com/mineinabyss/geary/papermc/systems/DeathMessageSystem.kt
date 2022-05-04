package com.mineinabyss.geary.papermc.systems

import com.mineinabyss.geary.papermc.access.toGeary
import com.mineinabyss.geary.papermc.components.DisplayName
import com.mineinabyss.geary.papermc.helpers.customMobType
import com.mineinabyss.idofront.messaging.miniMsg
import net.kyori.adventure.text.TranslatableComponent
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.PlayerDeathEvent

class DeathMessageSystem : Listener {
    @EventHandler
    fun PlayerDeathEvent.replaceMobName() {
        val damager = (player.lastDamageCause as? EntityDamageByEntityEvent)?.damager ?: return
        val name = damager.toGeary().get<DisplayName>()?.name ?: damager.customMobType
        val message = (deathMessage() as TranslatableComponent)
        val newMsg = message.args(listOf(message.args().first(), name.miniMsg()))
        deathMessage(newMsg)
    }
}
