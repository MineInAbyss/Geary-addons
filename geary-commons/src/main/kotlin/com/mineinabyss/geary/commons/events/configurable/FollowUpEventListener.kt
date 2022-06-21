package com.mineinabyss.geary.commons.events.configurable

import com.mineinabyss.geary.annotations.AutoScan
import com.mineinabyss.geary.annotations.Handler
import com.mineinabyss.geary.commons.events.configurable.components.EventCondition
import com.mineinabyss.geary.commons.events.configurable.components.TriggerWhenSource
import com.mineinabyss.geary.commons.events.configurable.components.TriggerWhenTarget
import com.mineinabyss.geary.components.RequestCheck
import com.mineinabyss.geary.components.events.FailedCheck
import com.mineinabyss.geary.datatypes.GearyComponentId
import com.mineinabyss.geary.datatypes.GearyEntity
import com.mineinabyss.geary.datatypes.GearyEntityId
import com.mineinabyss.geary.helpers.toGeary
import com.mineinabyss.geary.prefabs.helpers.addPrefab
import com.mineinabyss.geary.systems.GearyListener
import com.mineinabyss.geary.systems.accessors.EventScope
import com.mineinabyss.geary.systems.accessors.SourceScope
import com.mineinabyss.geary.systems.accessors.TargetScope

@AutoScan
class FollowUpEventListener : GearyListener() {
    @Handler
    fun tryFollowUpEvents(source: SourceScope, target: TargetScope, event: EventScope) {
        event.entity.type.forEach { comp: GearyComponentId ->
            fun Set<GearyEntityId>.runFollowUp(runAsSource: Boolean) {
                val withSource = if (runAsSource) source else target
                val withTarget = if (runAsSource) target else source
                map { it.toGeary() }.forEach { triggerEntity ->
                    val conditionEntity = withSource.entity.getRelation<EventCondition>(triggerEntity)?.entity
                    if (conditionEntity == null ||
                        withTarget.entity.callCheck(source = source.entity) {
                            addPrefab(conditionEntity.toGeary())
                        }
                    ) {
                        withTarget.entity.callEvent(triggerEntity, source = withSource.entity)
                    }
                }
            }

            val checkComp = comp.toGeary()
            source.entity.getRelation<TriggerWhenSource>(checkComp)?.apply {
                entities.runFollowUp(runAsSource)
            }
            target.entity.getRelation<TriggerWhenTarget>(checkComp)?.apply {
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
