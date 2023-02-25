package com.mineinabyss.geary.papermc.actions.systems

import com.mineinabyss.geary.annotations.Handler
import com.mineinabyss.geary.autoscan.AutoScan
import com.mineinabyss.geary.commons.events.configurable.components.Apply
import com.mineinabyss.geary.papermc.actions.components.ApplicableAttribute
import com.mineinabyss.geary.systems.GearyListener
import com.mineinabyss.geary.systems.accessors.EventScope
import com.mineinabyss.geary.systems.accessors.TargetScope
import com.mineinabyss.idofront.typealiases.BukkitEntity
import org.bukkit.entity.LivingEntity

/**
 * Modifies a specified attribute by a specifies amount.
 */
@AutoScan
class ApplyAttribute : GearyListener() {
    val TargetScope.bukkit by get<BukkitEntity>()
    val EventScope.attribute by getRelations<Apply?, ApplicableAttribute>()

    @Handler
    fun TargetScope.apply(event: EventScope) {
        val living: LivingEntity = bukkit as? LivingEntity ?: return
        val (attribute, amplifier) = event.attribute.targetData
        living.getAttribute(attribute)?.baseValue = amplifier
    }
}
