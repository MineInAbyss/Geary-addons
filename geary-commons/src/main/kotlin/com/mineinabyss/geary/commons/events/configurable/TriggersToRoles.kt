package com.mineinabyss.geary.commons.events.configurable

import com.mineinabyss.geary.annotations.AutoScan
import com.mineinabyss.geary.annotations.Handler
import com.mineinabyss.geary.commons.events.configurable.components.EventTriggers
import com.mineinabyss.geary.commons.events.configurable.components.TriggerWhenSource
import com.mineinabyss.geary.commons.events.configurable.components.TriggerWhenTarget
import com.mineinabyss.geary.serialization.parseEntity
import com.mineinabyss.geary.systems.GearyListener
import com.mineinabyss.geary.systems.accessors.TargetScope

@AutoScan
class TriggersToRoles : GearyListener() {
    val TargetScope.conditions by onSet<EventTriggers>()

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

                val causeEntity = entity.parseEntity(cause.removeTarget())
                val effectEntities = effect.removeTarget()
                    .split(", ?".toRegex())
                    .mapTo(mutableSetOf()) { entity.parseEntity(it).id }

                if (triggerWhenOnSource) {
                    val existing = entity.getRelation<TriggerWhenSource>(causeEntity)
                    if (existing != null) entity.setRelation(
                        existing.copy(entities = existing.entities.plus(effectEntities)),
                        causeEntity,
                    )
                    else entity.setRelation(TriggerWhenSource(entities = effectEntities, runAsSource), causeEntity)
                } else {
                    val existing = entity.getRelation<TriggerWhenTarget>(causeEntity)
                    if (existing != null) entity.setRelation(
                        existing.copy(entities = existing.entities.plus(effectEntities)),
                        causeEntity
                    )
                    else entity.setRelation(TriggerWhenTarget(entities = effectEntities, runAsSource), causeEntity)
                }
            }
        } finally {
            entity.remove<EventTriggers>()
        }
    }
}
