package com.mineinabyss.geary.papermc.systems

import com.mineinabyss.geary.annotations.AutoScan
import com.mineinabyss.geary.annotations.Handler
import com.mineinabyss.geary.papermc.components.SlimeSize
import com.mineinabyss.geary.systems.GearyListener
import com.mineinabyss.geary.systems.accessors.TargetScope
import com.mineinabyss.idofront.typealiases.BukkitEntity
import com.mineinabyss.idofront.util.randomOrMin
import org.bukkit.entity.Slime

@AutoScan
class SetSlimeSizeSystem : GearyListener() {
    private val TargetScope.slimeSize by added<SlimeSize>()
    private val TargetScope.bukkit by added<BukkitEntity>()

    @Handler
    fun TargetScope.apply() {
        val slime = (bukkit as? Slime) ?: return
        slime.size = slimeSize.size.randomOrMin()
    }
}