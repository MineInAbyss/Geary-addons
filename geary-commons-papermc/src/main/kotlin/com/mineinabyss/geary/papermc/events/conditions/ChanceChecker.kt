package com.mineinabyss.geary.papermc.events.conditions

import com.mineinabyss.geary.annotations.AutoScan
import com.mineinabyss.geary.annotations.Handler
import com.mineinabyss.geary.systems.GearyListener
import com.mineinabyss.geary.systems.accessors.EventScope
import com.mineinabyss.geary.systems.accessors.get
import kotlin.random.Random

@AutoScan
class ChanceChecker : GearyListener() {
    val EventScope.chance by get<Chance>()

    @Handler
    fun check(eventScope: EventScope): Boolean =
        Random.nextDouble() < eventScope.chance.percentage
}
