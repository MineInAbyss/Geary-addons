package com.mineinabyss.geary.commons.events.configurable

import com.mineinabyss.geary.commons.events.configurable.components.EventCondition
import com.mineinabyss.geary.commons.events.configurable.components.EventConditions
import com.mineinabyss.geary.ecs.accessors.TargetScope
import com.mineinabyss.geary.ecs.accessors.building.get
import com.mineinabyss.geary.ecs.api.autoscan.AutoScan
import com.mineinabyss.geary.ecs.api.autoscan.Handler
import com.mineinabyss.geary.ecs.api.systems.GearyListener
import com.mineinabyss.geary.ecs.serialization.parseEntity

@AutoScan
class ConditionsToRoles : GearyListener() {
    val TargetScope.triggers by get<EventConditions>()

    init {
        allAdded()
    }

    @Handler
    fun TargetScope.convert() {
        try {
            triggers.expressions.forEach { expression ->
                val (cause, condition, effect) = expression.replace(" ", "").split("->").takeIf { it.size == 3 }
                    ?: error("Expression needs to be formatted as 'cause -> condition -> effect'")
                cause == "any" || error("Only 'any' is currently supported as a cause.")
                entity.setRelation(
                    entity.parseEntity(effect).id,
                    EventCondition(entity = entity.parseEntity(condition).id)
                )
            }
        } finally {
            entity.remove<EventConditions>()
        }
    }
}
