package com.mineinabyss.geary.minecraft.systems.exposure

import com.mineinabyss.geary.ecs.accessors.TargetScope
import com.mineinabyss.geary.ecs.accessors.get
import com.mineinabyss.geary.ecs.api.autoscan.AutoScan
import com.mineinabyss.geary.ecs.api.systems.GearyListener
import com.mineinabyss.geary.ecs.api.systems.Handler
import com.mineinabyss.geary.ecs.serialization.parseEntity
import com.mineinabyss.geary.minecraft.systems.exposure.components.Apply
import com.mineinabyss.geary.minecraft.systems.exposure.components.ApplyBuild

@AutoScan
class ApplyToRelation : GearyListener() {
    private val TargetScope.apply by get<ApplyBuild>()

    init {
        allAdded()
    }

    @Handler
    private fun TargetScope.convertToRelation() {
        try {
            entity.setRelation(entity.parseEntity(apply.entityExpression).id, Apply)
        } finally {
            entity.remove<ApplyBuild>()
        }
    }
}
