package com.mineinabyss.geary.minecraft.systems.exposure

import com.mineinabyss.geary.ecs.accessors.EventScope
import com.mineinabyss.geary.ecs.accessors.SourceScope
import com.mineinabyss.geary.ecs.accessors.TargetScope
import com.mineinabyss.geary.ecs.api.GearyComponentId
import com.mineinabyss.geary.ecs.api.autoscan.AutoScan
import com.mineinabyss.geary.ecs.api.engine.type
import com.mineinabyss.geary.ecs.api.systems.GearyListener
import com.mineinabyss.geary.ecs.api.systems.Handler
import com.mineinabyss.geary.ecs.engine.ENTITY_MASK
import com.mineinabyss.geary.ecs.entities.addPrefab
import com.mineinabyss.geary.ecs.events.FailedCheck
import com.mineinabyss.geary.ecs.events.RequestCheck
import com.mineinabyss.geary.ecs.serialization.parseEntity
import com.mineinabyss.geary.minecraft.systems.exposure.components.EventCondition
import com.mineinabyss.geary.minecraft.systems.exposure.components.EventRun

@AutoScan
class FollowUpEventListener : GearyListener() {
    @Handler
    fun tryFollowUpEvents(source: SourceScope, target: TargetScope, event: EventScope) {
        event.entity.type.forEach { comp: GearyComponentId ->
            val trigger = source.entity.getRelation(comp and ENTITY_MASK, EventRun::class) ?: return@forEach
            val triggerEntity = source.entity.parseEntity(trigger.entity)
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

//@AutoScan
//class EventConditions : GearyListener() {
//    val SourceScope.exposure by relation<Any?, EventCondition>()
//    val EventScope
//
//    init {
//        event.has<Ingested>()
//    }
//
//    @Handler
//    fun onIngestion(source: SourceScope, target: TargetScope): Boolean {
//        target.entity.callEvent(source = source.entity) {
//            setRelation(Apply::class, source.exposure.key)
//        }
//    }
//}
