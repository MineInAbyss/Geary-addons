package com.mineinabyss.geary.minecraft.systems.exposure

import com.mineinabyss.geary.ecs.accessors.TargetScope
import com.mineinabyss.geary.ecs.accessors.get
import com.mineinabyss.geary.ecs.api.autoscan.AutoScan
import com.mineinabyss.geary.ecs.api.systems.GearyListener
import com.mineinabyss.geary.ecs.api.systems.Handler
import com.mineinabyss.geary.ecs.serialization.parseEntity
import com.mineinabyss.geary.minecraft.systems.exposure.components.EventCondition
import com.mineinabyss.geary.minecraft.systems.exposure.components.EventConditions
import com.mineinabyss.geary.minecraft.systems.exposure.components.EventTrigger
import com.mineinabyss.geary.minecraft.systems.exposure.components.EventTriggers

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
                entity.setRelation(entity.parseEntity(cause).id, EventTrigger(entity = entity.parseEntity(effect).id))
            }
        } finally {
            entity.remove<EventTriggers>()
        }
    }
}

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
                entity.setRelation(entity.parseEntity(effect).id, EventCondition(entity = condition))
            }
        } finally {
            entity.remove<EventConditions>()
        }
    }
}
