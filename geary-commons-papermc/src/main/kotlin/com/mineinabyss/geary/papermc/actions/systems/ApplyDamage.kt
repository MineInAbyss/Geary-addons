package com.mineinabyss.geary.papermc.actions.systems

import com.mineinabyss.geary.annotations.AutoScan
import com.mineinabyss.geary.annotations.Handler
import com.mineinabyss.geary.commons.events.configurable.components.Apply
import com.mineinabyss.geary.papermc.actions.components.DealDamage
import com.mineinabyss.geary.systems.GearyListener
import com.mineinabyss.geary.systems.accessors.EventScope
import com.mineinabyss.geary.systems.accessors.TargetScope
import com.mineinabyss.geary.systems.accessors.get
import com.mineinabyss.geary.systems.accessors.relation
import com.mineinabyss.idofront.typealiases.BukkitEntity
import com.mineinabyss.idofront.util.randomOrMin
import org.bukkit.entity.LivingEntity

@AutoScan
class ApplyDamage : GearyListener() {
    val TargetScope.bukkit by get<BukkitEntity>()
    val EventScope.damage by relation<DealDamage, Apply>()

    @Handler
    fun applyDamage(target: TargetScope, event: EventScope) {
        val livingTarget = target.bukkit as? LivingEntity ?: return

        with(event.damage.key) {
            val chosenDamage = damage.randomOrMin()
            //if true, damage dealt ignores armor, otherwise factors armor into damage calc
            livingTarget.health = (livingTarget.health - chosenDamage).coerceAtLeast(minHealth)
        }
    }
}
