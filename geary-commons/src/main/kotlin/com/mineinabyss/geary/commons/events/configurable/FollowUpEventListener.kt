package com.mineinabyss.geary.commons.events.configurable

import com.mineinabyss.geary.commons.events.configurable.components.EventCondition
import com.mineinabyss.geary.commons.events.configurable.components.EventTrigger
import com.mineinabyss.geary.ecs.accessors.EventScope
import com.mineinabyss.geary.ecs.accessors.SourceScope
import com.mineinabyss.geary.ecs.accessors.TargetScope
import com.mineinabyss.geary.ecs.api.GearyComponentId
import com.mineinabyss.geary.ecs.api.autoscan.AutoScan
import com.mineinabyss.geary.ecs.api.autoscan.Handler
import com.mineinabyss.geary.ecs.api.entities.toGeary
import com.mineinabyss.geary.ecs.api.systems.GearyListener
import com.mineinabyss.geary.ecs.engine.ENTITY_MASK
import com.mineinabyss.geary.ecs.events.FailedCheck
import com.mineinabyss.geary.ecs.events.RequestCheck
import com.mineinabyss.geary.ecs.serialization.parseEntity
import com.mineinabyss.geary.prefabs.helpers.addPrefab

@AutoScan
class FollowUpEventListener : GearyListener() {
    @Handler
    fun tryFollowUpEvents(source: SourceScope, target: TargetScope, event: EventScope) {
        event.entity.type.forEach { comp: GearyComponentId ->
            source.entity.getRelation(comp and ENTITY_MASK, EventTrigger::class)
                ?.entities?.map { it.toGeary() }?.forEach { triggerEntity ->
                    val conditionEntity = source.entity.getRelation(triggerEntity.id, EventCondition::class)
                        ?.let { source.entity.parseEntity(it.entity) }
                    if (conditionEntity == null || target.entity.callEvent(
                            init = {
                                addPrefab(conditionEntity)
                                set(RequestCheck)
                            },
                            source = source.entity,
                            result = { !it.has<FailedCheck>() })
                    ) {
                        target.entity.callEvent(triggerEntity, source = source.entity)
                    }
                }
        }
    }
}