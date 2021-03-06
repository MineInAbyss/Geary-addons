package com.mineinabyss.geary.commons.events.configurable

import com.mineinabyss.geary.annotations.AutoScan
import com.mineinabyss.geary.annotations.Handler
import com.mineinabyss.geary.commons.events.configurable.components.Apply
import com.mineinabyss.geary.commons.events.configurable.components.ApplyBuilder
import com.mineinabyss.geary.serialization.parseEntity
import com.mineinabyss.geary.systems.GearyListener
import com.mineinabyss.geary.systems.accessors.TargetScope

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
