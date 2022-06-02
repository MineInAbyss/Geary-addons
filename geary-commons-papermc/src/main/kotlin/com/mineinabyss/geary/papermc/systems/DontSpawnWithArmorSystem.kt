package com.mineinabyss.geary.papermc.systems

import com.mineinabyss.geary.annotations.AutoScan
import com.mineinabyss.geary.annotations.Handler
import com.mineinabyss.geary.systems.GearyListener
import com.mineinabyss.geary.systems.accessors.TargetScope
import com.mineinabyss.idofront.typealiases.BukkitEntity
import org.bukkit.entity.LivingEntity

@AutoScan
class DontSpawnWithArmorSystem : GearyListener() {
    private val TargetScope.armor by added<com.mineinabyss.geary.papermc.components.DontSpawnWithArmor>()
    private val TargetScope.bukkit by added<BukkitEntity>()

    @Handler
    fun TargetScope.apply() {
        when (val mob = bukkit) {
            is LivingEntity -> mob.equipment?.clear() ?: return
        }
    }
}
