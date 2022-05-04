package com.mineinabyss.geary.papermc.systems

import com.mineinabyss.geary.papermc.access.toGeary
import com.mineinabyss.geary.papermc.components.NoBreeding
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityBreedEvent
import org.bukkit.event.entity.EntityEnterLoveModeEvent

class NoBreedingSystem : Listener {
    @EventHandler
    fun EntityEnterLoveModeEvent.cancelLove() {
        if (entity.toGeary().has<NoBreeding>()) isCancelled = true
    }

    @EventHandler
    fun EntityBreedEvent.cancelBreed() {
        if (entity.toGeary().has<NoBreeding>()) isCancelled = true
    }
}
