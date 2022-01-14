package com.mineinabyss.geary.ecs.systems

import com.mineinabyss.geary.ecs.accessors.TargetScope
import com.mineinabyss.geary.ecs.accessors.building.relation
import com.mineinabyss.geary.ecs.api.autoscan.AutoScan
import com.mineinabyss.geary.ecs.api.systems.TickingSystem
import com.mineinabyss.geary.ecs.components.Expiry

/**
 * Handles removing components when an [Expiry] relation exists with another component.
 */
@AutoScan
class ExpiringComponentSystem : TickingSystem() {
    private val TargetScope.expiry by relation<Any?, Expiry>()

    override fun TargetScope.tick() {
        if (expiry.value.timeOver()) {
            entity.remove(expiry.keyId)
            entity.remove(expiry.relation.id)
        }
    }
}
