package com.mineinabyss.geary.papermc.actions.systems

import com.mineinabyss.geary.autoscan.AutoScan
import com.mineinabyss.geary.commons.events.configurable.components.Apply
import com.mineinabyss.geary.ecs.accessors.EventScope
import com.mineinabyss.geary.ecs.accessors.TargetScope
import com.mineinabyss.geary.ecs.accessors.building.get
import com.mineinabyss.geary.ecs.accessors.building.relation
import com.mineinabyss.geary.ecs.api.annotations.Handler
import com.mineinabyss.geary.ecs.api.systems.GearyListener
import com.mineinabyss.geary.ecs.api.systems.provideDelegate
import com.mineinabyss.geary.papermc.components.PotionEffects
import com.mineinabyss.idofront.typealiases.BukkitEntity
import org.bukkit.entity.LivingEntity

/**
 * Handles being exposed to potion effects
 */
@AutoScan
class ApplyPotionEffects : GearyListener() {
    val TargetScope.bukkit by get<BukkitEntity>()
    val EventScope.exposedEffects by relation<PotionEffects, Apply>()

    @Handler
    fun TargetScope.applyPotions(event: EventScope) {
        (bukkit as? LivingEntity)?.addPotionEffects(event.exposedEffects.key.effects)
    }
}
