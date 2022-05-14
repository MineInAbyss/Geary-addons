package com.mineinabyss.geary.commons.events.configurable

import com.mineinabyss.geary.annotations.AutoScan
import com.mineinabyss.geary.annotations.Handler
import com.mineinabyss.geary.commons.events.configurable.components.EventRun
import com.mineinabyss.geary.commons.events.configurable.components.EventRunBuilder
import com.mineinabyss.geary.serialization.parseEntity
import com.mineinabyss.geary.systems.GearyListener
import com.mineinabyss.geary.systems.accessors.EventScope
import com.mineinabyss.geary.systems.accessors.SourceScope
import com.mineinabyss.geary.systems.accessors.TargetScope

@AutoScan
class EventRunListener : GearyListener() {
    val EventScope.run by relation<Any?, EventRun>()

    @Handler
    fun handle(source: SourceScope, target: TargetScope, event: EventScope) {
        target.entity.callEvent(event.run.typeEntity, source = source.entity)
    }
}

@AutoScan
class EventRunBuilderToRelation : GearyListener() {
    val TargetScope.run by added<EventRunBuilder>()

    @Handler
    fun TargetScope.handle() {
        entity.setRelation(EventRun, entity.parseEntity(run.expression).id)
        entity.remove<EventRunBuilder>()
    }
}
