package com.mineinabyss.geary.commons.systems

import com.mineinabyss.geary.annotations.AutoScan
import com.mineinabyss.geary.commons.components.Expiry
import com.mineinabyss.geary.systems.TickingSystem
import com.mineinabyss.geary.systems.accessors.TargetScope

/**
 * Handles removing components when an [Expiry] relation exists with another component.
 */
@AutoScan
class ExpiringComponentSystem : TickingSystem() {
    private val TargetScope.expiry by relation<Any?, Expiry>()

    override fun TargetScope.tick() {
        if (expiry.target.timeOver()) {
            entity.remove(expiry.typeEntity.id)
            entity.remove(expiry.relation.id)
        }
    }
}
