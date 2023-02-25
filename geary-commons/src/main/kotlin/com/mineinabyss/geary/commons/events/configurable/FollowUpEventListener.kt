package com.mineinabyss.geary.commons.events.configurable

import com.mineinabyss.geary.autoscan.AutoScan
import com.mineinabyss.geary.annotations.Handler
import com.mineinabyss.geary.commons.events.configurable.components.EventCondition
import com.mineinabyss.geary.commons.events.configurable.components.TriggerWhenSource
import com.mineinabyss.geary.commons.events.configurable.components.TriggerWhenTarget
import com.mineinabyss.geary.components.RequestCheck
import com.mineinabyss.geary.components.events.FailedCheck
import com.mineinabyss.geary.datatypes.GearyEntity
import com.mineinabyss.geary.datatypes.GearyEntityType
import com.mineinabyss.geary.helpers.toGeary
import com.mineinabyss.geary.prefabs.helpers.addPrefab
import com.mineinabyss.geary.systems.GearyListener
import com.mineinabyss.geary.systems.accessors.EventScope
import com.mineinabyss.geary.systems.accessors.SourceScope
import com.mineinabyss.geary.systems.accessors.TargetScope

fun GearyEntityType.runFollowUp(runAsSource: Boolean, current: GearyEntity, other: GearyEntity) {
    val withSource = if (runAsSource) current else other
    val withTarget = if (runAsSource) other else current
    forEach {
        val triggerEntity = it.toGeary()
        val conditionEntity = withSource.getRelation<EventCondition>(triggerEntity)?.entity
        if (conditionEntity == null || withTarget.callCheck(source = current) {
                addPrefab(conditionEntity.toGeary())
            }) {
            withTarget.callEvent(triggerEntity, source = withSource)
        }
    }
}

@AutoScan
class TriggerWhenTargetListener : GearyListener() {
    val TargetScope.trigger by getRelations<TriggerWhenTarget, Any?>()

    @Handler
    fun TargetScope.tryFollowUpEvents(event: EventScope, source: SourceScope) {
        // If event has our trigger
        if (trigger.target.id in event.entity.type) {
            trigger.data.runEvents.runFollowUp(trigger.data.runAsSource, entity, source.entity)
        }
    }
}

@AutoScan
class TriggerWhenSourceListener : GearyListener() {
    val TargetScope.trigger by getRelations<TriggerWhenSource, Any?>()

    @Handler
    fun TargetScope.tryFollowUpEvents(event: EventScope, source: SourceScope) {
        if (trigger.target.id in event.entity.type) {
            trigger.data.runEvents.runFollowUp(trigger.data.runAsSource, entity, source.entity)
        }
    }
}


inline fun GearyEntity.callCheck(
    source: GearyEntity? = null,
    crossinline init: GearyEntity.() -> Unit,
): Boolean = callEvent(
    init = {
        init()
        add<RequestCheck>()
    },
    source = source,
    result = { !it.has<FailedCheck>() }
)
