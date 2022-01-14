package com.mineinabyss.geary.commons.events.configurable

import com.mineinabyss.geary.commons.events.configurable.components.Apply
import com.mineinabyss.geary.commons.events.configurable.components.ApplyBuilder
import com.mineinabyss.geary.ecs.accessors.TargetScope
import com.mineinabyss.geary.ecs.accessors.building.get
import com.mineinabyss.geary.ecs.api.autoscan.AutoScan
import com.mineinabyss.geary.ecs.api.autoscan.Handler
import com.mineinabyss.geary.ecs.api.systems.GearyListener
import com.mineinabyss.geary.ecs.serialization.parseEntity

@AutoScan
public class ParseApply : GearyListener() {
    private val TargetScope.apply by get<ApplyBuilder>()

    init {
        allAdded()
    }

    @Handler
    private fun TargetScope.convertToRelation() {
        try {
            entity.setRelation(entity.parseEntity(apply.entityExpression).id, Apply)
        } finally {
            entity.remove<ApplyBuilder>()
        }
    }
}
