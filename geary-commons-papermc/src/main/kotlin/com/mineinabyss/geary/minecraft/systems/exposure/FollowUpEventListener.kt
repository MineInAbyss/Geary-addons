package com.mineinabyss.geary.minecraft.systems.exposure

import com.mineinabyss.geary.ecs.accessors.EventScope
import com.mineinabyss.geary.ecs.accessors.SourceScope
import com.mineinabyss.geary.ecs.accessors.TargetScope
import com.mineinabyss.geary.ecs.api.GearyComponentId
import com.mineinabyss.geary.ecs.api.autoscan.AutoScan
import com.mineinabyss.geary.ecs.api.engine.type
import com.mineinabyss.geary.ecs.api.entities.toGeary
import com.mineinabyss.geary.ecs.api.systems.GearyListener
import com.mineinabyss.geary.ecs.api.systems.Handler
import com.mineinabyss.geary.ecs.engine.ENTITY_MASK
import com.mineinabyss.geary.ecs.entities.addPrefab
import com.mineinabyss.geary.ecs.events.FailedCheck
import com.mineinabyss.geary.ecs.events.RequestCheck
import com.mineinabyss.geary.ecs.serialization.parseEntity
import com.mineinabyss.geary.minecraft.systems.exposure.components.EventCondition
import com.mineinabyss.geary.minecraft.systems.exposure.components.EventTrigger

@AutoScan
class FollowUpEventListener : GearyListener() {
    @Handler
    fun tryFollowUpEvents(source: SourceScope, target: TargetScope, event: EventScope) {
        event.entity.type.forEach { comp: GearyComponentId ->
            val triggerEntity = source.entity.getRelation(comp and ENTITY_MASK, EventTrigger::class)?.entity?.toGeary()
                ?: return@forEach
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
