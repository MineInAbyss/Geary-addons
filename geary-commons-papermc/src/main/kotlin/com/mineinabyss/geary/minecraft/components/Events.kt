package com.mineinabyss.geary.minecraft.components

import com.mineinabyss.geary.ecs.api.actions.GearyAction
import com.mineinabyss.geary.ecs.api.autoscan.AutoscanComponent
import com.mineinabyss.geary.ecs.api.entities.GearyEntity
import com.mineinabyss.geary.ecs.serialization.FlatSerializer
import com.mineinabyss.geary.ecs.serialization.FlatWrap
import com.mineinabyss.geary.minecraft.actions.CancelEventAction
import kotlinx.serialization.Serializable
import kotlinx.serialization.serializer
import org.bukkit.event.Cancellable
import org.bukkit.event.Event

/**
 * Stores a map of strings to actions which fire based on different game events.
 */
@Serializable(with = EventComponentSerializer::class)
@AutoscanComponent
public data class Events(
    override val wrapped: Map<String, List<GearyAction>>
) : FlatWrap<Map<String, List<GearyAction>>>

public object EventComponentSerializer : FlatSerializer<Events, Map<String, List<GearyAction>>>(
    "geary:events", serializer(), { Events(it) }
)

public fun Event.event(entity: GearyEntity?, name: String) {
    entity?.get<Events>()?.wrapped?.get(name)?.forEach {
        it(entity)
        if (it is CancelEventAction && this is Cancellable)
            isCancelled = true
    }
}
