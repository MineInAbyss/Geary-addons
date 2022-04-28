package com.mineinabyss.geary.papermc.systems

import com.mineinabyss.geary.annotations.AutoScan
import com.mineinabyss.geary.annotations.Handler
import com.mineinabyss.geary.datatypes.family.MutableFamilyOperations.Companion.has
import com.mineinabyss.geary.datatypes.family.family
import com.mineinabyss.geary.papermc.components.NoVanillaInteractions
import com.mineinabyss.geary.papermc.events.bridge.components.Interacted
import com.mineinabyss.geary.systems.GearyListener
import com.mineinabyss.geary.systems.accessors.EventScope
import com.mineinabyss.geary.systems.accessors.SourceScope
import com.mineinabyss.geary.systems.accessors.building.map
import com.mineinabyss.geary.systems.accessors.get
import org.bukkit.event.Cancellable
import org.bukkit.event.Event

@AutoScan
class NoItemInteractionsListener : GearyListener() {
    private val EventScope.bukkit by get<Event>().map { it as? Cancellable }
    private val EventScope.interacted by family { has<Interacted>() }
    private val SourceScope.noVanilla by family { has<NoVanillaInteractions>() }

    @Handler
    fun onPlace(event: EventScope) {
        event.bukkit?.isCancelled = true
    }
}
