package com.mineinabyss.geary.papermc.systems

import com.mineinabyss.geary.annotations.AutoScan
import com.mineinabyss.geary.annotations.Handler
import com.mineinabyss.geary.papermc.components.DontBurnInDay
import com.mineinabyss.geary.systems.GearyListener
import com.mineinabyss.geary.systems.accessors.TargetScope
import com.mineinabyss.idofront.typealiases.BukkitEntity
import org.bukkit.entity.AbstractSkeleton
import org.bukkit.entity.Phantom
import org.bukkit.entity.Zombie

@AutoScan
class DontBurnInDaySystem : GearyListener() {
    private val TargetScope.burn by added<DontBurnInDay>()
    private val TargetScope.bukkit by added<BukkitEntity>()

    @Handler
    fun TargetScope.apply() {
        when (val mob = bukkit) {
            is Phantom -> mob.setShouldBurnInDay(false)
            is AbstractSkeleton -> mob.setShouldBurnInDay(false)
            is Zombie -> mob.setShouldBurnInDay(false)
        }
    }
}