package com.mineinabyss.geary.papermc.actions

import com.mineinabyss.geary.datatypes.GearyEntity
import com.mineinabyss.geary.papermc.gearyCommonsPlugin
import com.mineinabyss.idofront.time.inWholeTicks
import com.mineinabyss.idofront.typealiases.BukkitEntity
import com.okkero.skedule.schedule
import kotlin.time.Duration

/**
 * Applies the max freezing effect (from being in powdered snow) to an entity
 * for a number of seconds.
 */
fun GearyEntity.chill(
    length: Duration,
    entity: BukkitEntity? = get()
): Boolean {
    entity ?: return false

    gearyCommonsPlugin.schedule {
        var timePassed = 0L
        val lastTime = System.currentTimeMillis()
        while (timePassed < length.inWholeTicks) {
            entity.freezeTicks = entity.maxFreezeTicks
            timePassed += System.currentTimeMillis() - lastTime
            waitFor(1)
        }
    }
    return true
}
