package com.mineinabyss.geary.papermc.actions.systems

import com.mineinabyss.geary.annotations.AutoScan
import com.mineinabyss.geary.annotations.Handler
import com.mineinabyss.geary.commons.events.configurable.components.Apply
import com.mineinabyss.geary.papermc.components.PotionEffects
import com.mineinabyss.geary.systems.GearyListener
import com.mineinabyss.geary.systems.accessors.EventScope
import com.mineinabyss.geary.systems.accessors.TargetScope
import com.mineinabyss.idofront.typealiases.BukkitEntity
import org.bukkit.entity.LivingEntity

/**
 * Handles being exposed to potion effects
 */
@AutoScan
class ApplyPotionEffects : GearyListener() {
    val TargetScope.bukkit by get<BukkitEntity>()
    val EventScope.exposedEffects by getRelations<Apply?, PotionEffects>()

    @Handler
    fun TargetScope.applyPotions(event: EventScope) {
        (bukkit as? LivingEntity)?.addPotionEffects(event.exposedEffects.targetData.effects)
    }
}
