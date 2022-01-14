package com.mineinabyss.geary.commons.events.configurable

import com.mineinabyss.geary.commons.events.configurable.components.EventTrigger
import com.mineinabyss.geary.commons.events.configurable.components.EventTriggers
import com.mineinabyss.geary.ecs.accessors.TargetScope
import com.mineinabyss.geary.ecs.accessors.building.get
import com.mineinabyss.geary.ecs.api.autoscan.AutoScan
import com.mineinabyss.geary.ecs.api.autoscan.Handler
import com.mineinabyss.geary.ecs.api.systems.GearyListener
import com.mineinabyss.geary.ecs.serialization.parseEntity

@AutoScan
class TriggersToRoles : GearyListener() {
    val TargetScope.conditions by get<EventTriggers>()

    init {
        allAdded()
    }

    @Handler
    fun TargetScope.convert() {
        try {
            conditions.expressions.forEach { expression ->
                val (cause, effect) = expression.replace(" ", "").split("->").takeIf { it.size == 2 }
                    ?: error("Expression needs to be formatted as 'cause -> effect'")
                val key = entity.parseEntity(cause).id
                val value = entity.parseEntity(effect).id
                val existing = entity.getRelation(key, EventTrigger::class)
                if (existing != null)
                    entity.setRelation(key, existing.copy(entities = existing.entities.plus(value)))
                else
                    entity.setRelation(key, EventTrigger(entities = setOf(value)))
            }
        } finally {
            entity.remove<EventTriggers>()
        }
    }
}
