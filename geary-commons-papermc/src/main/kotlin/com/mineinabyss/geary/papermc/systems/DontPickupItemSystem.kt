package com.mineinabyss.geary.papermc.systems

import com.mineinabyss.geary.annotations.AutoScan
import com.mineinabyss.geary.annotations.Handler
import com.mineinabyss.geary.papermc.components.DontPickupItems
import com.mineinabyss.geary.systems.GearyListener
import com.mineinabyss.geary.systems.accessors.TargetScope
import com.mineinabyss.idofront.typealiases.BukkitEntity
import org.bukkit.entity.LivingEntity

@AutoScan
class DontPickupItemSystem : GearyListener() {
    private val TargetScope.pickup by added<DontPickupItems>()
    private val TargetScope.bukkit by added<BukkitEntity>()

    @Handler
    fun TargetScope.apply() {
        when (val mob = bukkit) {
            is LivingEntity -> mob.canPickupItems = false
        }
    }
}
