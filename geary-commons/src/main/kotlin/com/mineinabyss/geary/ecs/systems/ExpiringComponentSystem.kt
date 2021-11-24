package com.mineinabyss.geary.ecs.systems

import com.mineinabyss.geary.ecs.accessors.ResultScope
import com.mineinabyss.geary.ecs.api.systems.TickingSystem
import com.mineinabyss.geary.ecs.components.Expiry

/**
 * Handles removing components when an [Expiry] relation exists with another component.
 */
public object ExpiringComponentSystem : TickingSystem() {
    private val ResultScope.expiry by relation<Expiry>()

    override fun ResultScope.tick() {
        if (expiry.parentData.timeOver()) {
            entity.remove(expiry.component.id)
            entity.remove(expiry.relation.id)
        }
    }
}
