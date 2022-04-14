package com.mineinabyss.geary.commons.events.configurable

import com.mineinabyss.geary.autoscan.AutoScan
import com.mineinabyss.geary.commons.events.configurable.components.EventRun
import com.mineinabyss.geary.commons.events.configurable.components.EventRunBuilder
import com.mineinabyss.geary.ecs.accessors.EventScope
import com.mineinabyss.geary.ecs.accessors.SourceScope
import com.mineinabyss.geary.ecs.accessors.TargetScope
import com.mineinabyss.geary.ecs.accessors.building.relation
import com.mineinabyss.geary.ecs.api.annotations.Handler
import com.mineinabyss.geary.ecs.api.systems.GearyListener
import com.mineinabyss.geary.ecs.api.systems.provideDelegate
import com.mineinabyss.geary.ecs.serialization.parseEntity

@AutoScan
class EventRunListener : GearyListener() {
    val EventScope.run by relation<Any?, EventRun>()

    @Handler
    fun handle(source: SourceScope, target: TargetScope, event: EventScope) {
        target.entity.callEvent(event.run.keyId, source = source.entity)
    }
}

@AutoScan
class EventRunBuilderToRelation : GearyListener() {
    val TargetScope.run by added<EventRunBuilder>()

    @Handler
    fun TargetScope.handle() {
        entity.setRelation(entity.parseEntity(run.expression).id, EventRun)
        entity.remove<EventRunBuilder>()
    }
}
