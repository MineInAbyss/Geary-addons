package com.mineinabyss.geary.commons.events.configurable

import com.mineinabyss.geary.annotations.AutoScan
import com.mineinabyss.geary.annotations.Handler
import com.mineinabyss.geary.commons.events.configurable.components.Apply
import com.mineinabyss.geary.commons.events.configurable.components.ApplyBuilder
import com.mineinabyss.geary.serialization.parseEntity
import com.mineinabyss.geary.systems.GearyListener
import com.mineinabyss.geary.systems.accessors.TargetScope

@AutoScan
class ParseApply : GearyListener() {
    private val TargetScope.apply by onSet<ApplyBuilder>()

    @Handler
    private fun TargetScope.convertToRelation() {
        try {
            entity.addRelation<Apply>(entity.parseEntity(apply.entityExpression))
        } finally {
            entity.remove<ApplyBuilder>()
        }
    }
}
