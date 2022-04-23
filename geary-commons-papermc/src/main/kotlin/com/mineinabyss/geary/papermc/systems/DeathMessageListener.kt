package com.mineinabyss.geary.papermc.systems

import com.mineinabyss.geary.papermc.access.toGeary
import com.mineinabyss.geary.papermc.components.DisplayName
import com.mineinabyss.geary.prefabs.PrefabKey
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TranslatableComponent
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.PlayerDeathEvent

object DeathMessageListener: Listener {
    @EventHandler
    fun PlayerDeathEvent.replaceMobName() {
        val damager = (player.lastDamageCause as? EntityDamageByEntityEvent)?.damager?.toGeary() ?: return
        val name = damager.get<DisplayName>()?.name ?: damager.get<PrefabKey>()?.key ?: return
        val message = (deathMessage() as TranslatableComponent)
        val newMsg = message.args(listOf(message.args().first(), Component.text(name)))
        deathMessage(newMsg)
    }
}
