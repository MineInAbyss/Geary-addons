package com.mineinabyss.geary.papermc.systems

import com.mineinabyss.geary.annotations.AutoScan
import com.mineinabyss.geary.annotations.Handler
import com.mineinabyss.geary.papermc.components.BreakDownDoor
import com.mineinabyss.geary.systems.GearyListener
import com.mineinabyss.geary.systems.accessors.TargetScope
import com.mineinabyss.idofront.typealiases.BukkitEntity
import org.bukkit.entity.Zombie

@AutoScan
class BreakDownDoor : GearyListener() {
    private val TargetScope.breakDoor by added<BreakDownDoor>()
    private val TargetScope.bukkit by added<BukkitEntity>()

    @Handler
    fun TargetScope.apply() {
        when (val mob = bukkit) {
            is Zombie -> if (mob.supportsBreakingDoors()) mob.setCanBreakDoors(breakDoor.canBreakDoor)
        }
    }
}
