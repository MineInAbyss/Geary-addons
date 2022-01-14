package com.mineinabyss.geary.minecraft.components.item

import com.mineinabyss.geary.ecs.accessors.EventScope
import com.mineinabyss.geary.ecs.accessors.building.get
import com.mineinabyss.geary.ecs.accessors.building.map
import com.mineinabyss.geary.ecs.api.autoscan.AutoScan
import com.mineinabyss.geary.ecs.api.autoscan.Handler
import com.mineinabyss.geary.ecs.api.systems.GearyListener
import com.mineinabyss.geary.minecraft.events.bridge.components.Interacted
import org.bukkit.event.Cancellable
import org.bukkit.event.Event

@AutoScan
class NoInteractionsListener : GearyListener() {
    val EventScope.bukkit by get<Event>().map { it as? Cancellable }

    init {
        source.has<NoVanillaInteractions>()
        event.has<Interacted>()
    }

    @Handler
    fun onPlace(event: EventScope) {
        event.bukkit?.isCancelled = true
    }
}
