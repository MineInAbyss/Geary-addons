package com.mineinabyss.geary.papermc.systems

import com.mineinabyss.geary.papermc.access.toGeary
import com.mineinabyss.geary.papermc.components.DisplayName
import com.mineinabyss.idofront.messaging.miniMsg
import net.kyori.adventure.text.TranslatableComponent
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import java.util.*

class DeathMessageSystem : Listener {
    @EventHandler
    fun PlayerDeathEvent.replaceMobName() {
        val message = (deathMessage() as TranslatableComponent)
        val args = message.args().toMutableList()
        val entityIndex = args.indexOfFirst { (it is TranslatableComponent) && it.key().startsWith("entity") }
        val entity = Bukkit.getEntity(UUID.fromString((args[entityIndex] as TranslatableComponent).insertion()))
        val name = entity?.toGeary()?.get<DisplayName>()?.name ?: return
        args[entityIndex] = name.miniMsg()
        val newMsg = message.args(args)
        deathMessage(newMsg)
    }
}
