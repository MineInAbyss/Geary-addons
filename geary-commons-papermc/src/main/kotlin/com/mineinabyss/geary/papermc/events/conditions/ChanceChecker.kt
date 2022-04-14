package com.mineinabyss.geary.papermc.events.conditions

import com.mineinabyss.geary.autoscan.AutoScan
import com.mineinabyss.geary.ecs.accessors.EventScope
import com.mineinabyss.geary.ecs.accessors.building.get
import com.mineinabyss.geary.ecs.api.annotations.Handler
import com.mineinabyss.geary.ecs.api.systems.GearyListener
import com.mineinabyss.geary.ecs.api.systems.provideDelegate
import kotlin.random.Random

@AutoScan
class ChanceChecker : GearyListener() {
    val EventScope.chance by get<Chance>()

    @Handler
    fun check(eventScope: EventScope): Boolean =
        Random.nextDouble() < eventScope.chance.percentage
}
