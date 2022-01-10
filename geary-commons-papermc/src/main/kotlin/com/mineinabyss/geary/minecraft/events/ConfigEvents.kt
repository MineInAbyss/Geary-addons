package com.mineinabyss.geary.minecraft.events

import com.mineinabyss.geary.ecs.api.entities.GearyEntity
import com.mineinabyss.geary.ecs.serialization.FlatSerializer
import com.mineinabyss.geary.ecs.serialization.FlatWrap
import com.mineinabyss.geary.ecs.serialization.GearyEntitySerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer

/**
 * Stores a map of strings to actions which fire based on different game events.
 */
@Serializable(with = EventComponentSerializer::class)
data class ConfigEvents(
    override val wrapped: Map<String, List<@Serializable(with = GearyEntitySerializer::class) GearyEntity>>
) : FlatWrap<Map<String, List<GearyEntity>>>

object EventComponentSerializer : FlatSerializer<ConfigEvents, Map<String, List<GearyEntity>>>(
    "geary:events", MapSerializer(String.serializer(), ListSerializer(GearyEntitySerializer)), { ConfigEvents(it) }
)
