package com.mineinabyss.geary.commons.events.configurable

import com.mineinabyss.geary.autoscan.AutoScan
import com.mineinabyss.geary.commons.events.configurable.components.EventTriggers
import com.mineinabyss.geary.commons.events.configurable.components.TriggerWhenSource
import com.mineinabyss.geary.commons.events.configurable.components.TriggerWhenTarget
import com.mineinabyss.geary.ecs.accessors.TargetScope
import com.mineinabyss.geary.ecs.api.annotations.Handler
import com.mineinabyss.geary.ecs.api.systems.GearyListener
import com.mineinabyss.geary.ecs.api.systems.provideDelegate
import com.mineinabyss.geary.ecs.serialization.parseEntity

@AutoScan
class TriggersToRoles : GearyListener() {
    val TargetScope.conditions by added<EventTriggers>()

    @Handler
    fun TargetScope.convert() {
        try {
            conditions.expressions.forEach { expression ->
                val (cause, effect) = expression.split(" ?-> ?".toRegex()).takeIf { it.size == 2 }
                    ?: error("Expression needs to be formatted as 'cause -> effect'")
                fun String.isSource() = !endsWith(" this")
                fun String.removeTarget() = removePrefix("this ").removeSuffix(" this")
                val triggerWhenOnSource = cause.isSource()
                val runAsSource = effect.isSource()

                val causeEntity = entity.parseEntity(cause.removeTarget()).id
                val effectEntities = effect.removeTarget()
                    .split(", ?".toRegex())
                    .mapTo(mutableSetOf()) { entity.parseEntity(it).id }

                if (triggerWhenOnSource) {
                    val existing = entity.getRelation(causeEntity, TriggerWhenSource::class)
                    if (existing != null) entity.setRelation(
                        causeEntity,
                        existing.copy(entities = existing.entities.plus(effectEntities))
                    )
                    else entity.setRelation(causeEntity, TriggerWhenSource(entities = effectEntities, runAsSource))
                } else {
                    val existing = entity.getRelation(causeEntity, TriggerWhenTarget::class)
                    if (existing != null) entity.setRelation(
                        causeEntity,
                        existing.copy(entities = existing.entities.plus(effectEntities))
                    )
                    else entity.setRelation(causeEntity, TriggerWhenTarget(entities = effectEntities, runAsSource))
                }
            }
        } finally {
            entity.remove<EventTriggers>()
        }
    }
}
