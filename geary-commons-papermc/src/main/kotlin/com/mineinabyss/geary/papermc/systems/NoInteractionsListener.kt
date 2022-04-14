package com.mineinabyss.geary.papermc.systems

import com.mineinabyss.geary.autoscan.AutoScan
import com.mineinabyss.geary.ecs.accessors.EventScope
import com.mineinabyss.geary.ecs.accessors.building.get
import com.mineinabyss.geary.ecs.accessors.building.map
import com.mineinabyss.geary.ecs.api.annotations.Handler
import com.mineinabyss.geary.ecs.api.systems.GearyListener
import com.mineinabyss.geary.ecs.api.systems.provideDelegate
import com.mineinabyss.geary.papermc.components.item.NoVanillaInteractions
import com.mineinabyss.geary.papermc.events.bridge.components.Interacted
import org.bukkit.event.Cancellable
import org.bukkit.event.Event

@AutoScan
class NoInteractionsListener : GearyListener() {
    val EventScope.bukkit by get<Event>().map { it as? Cancellable }

    override fun onStart() {
        source.has<NoVanillaInteractions>()
        event.has<Interacted>()
    }

    @Handler
    fun onPlace(event: EventScope) {
        event.bukkit?.isCancelled = true
    }
}
