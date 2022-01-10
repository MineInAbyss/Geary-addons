package com.mineinabyss.geary.minecraft.events

import com.mineinabyss.geary.ecs.accessors.EventScope
import com.mineinabyss.geary.ecs.accessors.TargetScope
import com.mineinabyss.geary.ecs.accessors.get
import com.mineinabyss.geary.ecs.accessors.map
import com.mineinabyss.geary.ecs.api.autoscan.AutoScan
import com.mineinabyss.geary.ecs.api.engine.Engine
import com.mineinabyss.geary.ecs.api.engine.entity
import com.mineinabyss.geary.ecs.api.systems.GearyListener
import com.mineinabyss.geary.ecs.api.systems.Handler
import com.mineinabyss.geary.ecs.entities.addPrefab
import com.mineinabyss.geary.ecs.helpers.serialName
import com.mineinabyss.geary.minecraft.components.CancelBukkitEvent
import org.bukkit.event.Cancellable
import org.bukkit.event.Event

@AutoScan
class ConfigEventsListener : GearyListener() {
    val TargetScope.configEvents by get<ConfigEvents>()

    val EventScope.bukkitEvent by get<Event>().map { it as? Cancellable }

    @Handler
    fun handleConfigEvents(target: TargetScope, event: EventScope) {
        val components = event.entity.getComponents()

        // Get serial names of all components on this event
        components.mapNotNull { it.serialName?.let { target.configEvents.wrapped[it] } }
            .forEach {
                it.forEach { eventPrefab ->
                    val instance = Engine.entity { addPrefab(eventPrefab) }
                    target.entity.callEvent(instance)
                    if (instance.has<CancelBukkitEvent>())
                        event.bukkitEvent?.isCancelled = true
                }
            }
    }
}
