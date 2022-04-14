package com.mineinabyss.geary.commons.events.configurable

import com.mineinabyss.geary.autoscan.AutoScan
import com.mineinabyss.geary.commons.events.configurable.components.Apply
import com.mineinabyss.geary.commons.events.configurable.components.ApplyBuilder
import com.mineinabyss.geary.ecs.accessors.TargetScope
import com.mineinabyss.geary.ecs.api.annotations.Handler
import com.mineinabyss.geary.ecs.api.systems.GearyListener
import com.mineinabyss.geary.ecs.api.systems.provideDelegate
import com.mineinabyss.geary.ecs.serialization.parseEntity

@AutoScan
public class ParseApply : GearyListener() {
    private val TargetScope.apply by added<ApplyBuilder>()

    @Handler
    private fun TargetScope.convertToRelation() {
        try {
            entity.setRelation(entity.parseEntity(apply.entityExpression).id, Apply)
        } finally {
            entity.remove<ApplyBuilder>()
        }
    }
}
