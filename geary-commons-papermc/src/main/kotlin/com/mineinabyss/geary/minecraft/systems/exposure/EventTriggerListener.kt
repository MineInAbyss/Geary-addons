package com.mineinabyss.geary.minecraft.systems.exposure

import com.mineinabyss.geary.ecs.accessors.*
import com.mineinabyss.geary.ecs.api.autoscan.AutoScan
import com.mineinabyss.geary.ecs.api.systems.GearyListener
import com.mineinabyss.geary.ecs.api.systems.Handler
import com.mineinabyss.geary.ecs.serialization.parseEntity
import com.mineinabyss.geary.minecraft.systems.exposure.components.EventRun
import com.mineinabyss.geary.minecraft.systems.exposure.components.EventRunBuilder

@AutoScan
class EventRunListener: GearyListener() {
    val EventScope.run by relation<Any?, EventRun>()

    @Handler
    fun handle(source: SourceScope, target: TargetScope, event: EventScope) {
        target.entity.callEvent(event.run.keyId, source = source.entity)
    }
}

@AutoScan
class EventRunBuilderToRelation: GearyListener() {
    val TargetScope.run by get<EventRunBuilder>()

    init {
        allAdded()
    }

    @Handler
    fun TargetScope.handle() {
        entity.setRelation(entity.parseEntity(run.expression).id, EventRun)
        entity.remove<EventRunBuilder>()
    }
}
