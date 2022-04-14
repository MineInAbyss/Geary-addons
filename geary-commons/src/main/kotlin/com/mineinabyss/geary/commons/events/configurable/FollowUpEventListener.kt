package com.mineinabyss.geary.commons.events.configurable

import com.mineinabyss.geary.autoscan.AutoScan
import com.mineinabyss.geary.commons.events.configurable.components.EventCondition
import com.mineinabyss.geary.commons.events.configurable.components.TriggerWhenSource
import com.mineinabyss.geary.commons.events.configurable.components.TriggerWhenTarget
import com.mineinabyss.geary.ecs.accessors.EventScope
import com.mineinabyss.geary.ecs.accessors.SourceScope
import com.mineinabyss.geary.ecs.accessors.TargetScope
import com.mineinabyss.geary.ecs.api.GearyComponentId
import com.mineinabyss.geary.ecs.api.GearyEntityId
import com.mineinabyss.geary.ecs.api.annotations.Handler
import com.mineinabyss.geary.ecs.api.entities.GearyEntity
import com.mineinabyss.geary.ecs.api.entities.toGeary
import com.mineinabyss.geary.ecs.api.systems.GearyListener
import com.mineinabyss.geary.ecs.engine.ENTITY_MASK
import com.mineinabyss.geary.ecs.events.FailedCheck
import com.mineinabyss.geary.ecs.events.RequestCheck
import com.mineinabyss.geary.prefabs.helpers.addPrefab

@AutoScan
class FollowUpEventListener : GearyListener() {
    @Handler
    fun tryFollowUpEvents(source: SourceScope, target: TargetScope, event: EventScope) {
        event.entity.type.forEach { comp: GearyComponentId ->
            fun Set<GearyEntityId>.runFollowUp(runAsSource: Boolean) {
                val withSource = if(runAsSource) source else target
                val withTarget = if(runAsSource) target else source
                map { it.toGeary() }.forEach { triggerEntity ->
                    val conditionEntity = withSource.entity.getRelation(triggerEntity.id, EventCondition::class)?.entity
                    if (conditionEntity == null ||
                        withTarget.entity.callCheck(source = source.entity) {
                            addPrefab(conditionEntity.toGeary())
                        }
                    ) {
                        withTarget.entity.callEvent(triggerEntity, source = withSource.entity)
                    }
                }
            }

            val checkComp = comp and ENTITY_MASK
            source.entity.getRelation(checkComp, TriggerWhenSource::class)?.apply {
                entities.runFollowUp(runAsSource)
            }
            target.entity.getRelation(checkComp, TriggerWhenTarget::class)?.apply {
                // Use target as source if runAsSource is true.
                entities.runFollowUp(!runAsSource)
            }
        }
    }
}

inline fun GearyEntity.callCheck(
    source: GearyEntity? = null,
    crossinline init: GearyEntity.() -> Unit,
): Boolean = callEvent(
    init = {
        init()
        set(RequestCheck)
    },
    source = source,
    result = { !it.has<FailedCheck>() }
)
