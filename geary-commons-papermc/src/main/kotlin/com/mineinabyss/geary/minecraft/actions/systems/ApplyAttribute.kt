package com.mineinabyss.geary.minecraft.actions.systems

import com.mineinabyss.geary.commons.events.configurable.components.Apply
import com.mineinabyss.geary.ecs.accessors.EventScope
import com.mineinabyss.geary.ecs.accessors.TargetScope
import com.mineinabyss.geary.ecs.accessors.building.get
import com.mineinabyss.geary.ecs.accessors.building.relation
import com.mineinabyss.geary.ecs.api.autoscan.AutoScan
import com.mineinabyss.geary.ecs.api.autoscan.Handler
import com.mineinabyss.geary.ecs.api.systems.GearyListener
import com.mineinabyss.geary.minecraft.actions.components.ApplicableAttribute
import com.mineinabyss.idofront.typealiases.BukkitEntity
import org.bukkit.entity.LivingEntity

/**
 * Modifies a specified attribute by a specifies amount.
 */
@AutoScan
class ApplyAttribute: GearyListener() {
    val TargetScope.bukkit by get<BukkitEntity>()
    val EventScope.attribute by relation<ApplicableAttribute, Apply>()

    @Handler
    fun TargetScope.apply(event: EventScope) {
        val living: LivingEntity = bukkit as? LivingEntity ?: return
        val (attribute, amplifier) = event.attribute.key
        living.getAttribute(attribute)?.baseValue = amplifier
    }
}
