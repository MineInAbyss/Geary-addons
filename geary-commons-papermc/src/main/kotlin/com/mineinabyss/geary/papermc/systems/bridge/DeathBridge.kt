package com.mineinabyss.geary.papermc.systems.bridge

import com.mineinabyss.geary.commons.components.Dead
import com.mineinabyss.geary.papermc.access.toGeary
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDeathEvent

class DeathBridge: Listener {
    @EventHandler
    fun EntityDeathEvent.addDeadComponent() {
        entity.toGeary().set(Dead())
    }
}
