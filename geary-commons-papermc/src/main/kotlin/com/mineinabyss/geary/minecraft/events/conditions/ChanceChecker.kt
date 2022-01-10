package com.mineinabyss.geary.minecraft.events.conditions

import com.mineinabyss.geary.ecs.accessors.EventScope
import com.mineinabyss.geary.ecs.accessors.get
import com.mineinabyss.geary.ecs.api.autoscan.AutoScan
import com.mineinabyss.geary.ecs.api.systems.GearyListener
import com.mineinabyss.geary.ecs.api.systems.Handler
import kotlin.random.Random

@AutoScan
class ChanceChecker : GearyListener() {
    val EventScope.chance by get<Chance>()

    @Handler
    fun check(eventScope: EventScope): Boolean =
        Random.nextDouble() < eventScope.chance.percentage
}
