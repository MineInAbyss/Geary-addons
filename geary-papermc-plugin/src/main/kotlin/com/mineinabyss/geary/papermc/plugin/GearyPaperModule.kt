package com.mineinabyss.geary.papermc.plugin

import com.mineinabyss.geary.papermc.access.BukkitEntity2Geary
import com.mineinabyss.geary.uuid.UUID2GearyMap
import com.mineinabyss.idofront.di.DI

val gearyPaper: GearyPaperModule by DI.observe()

class GearyPaperModule(
    val plugin: GearyPlugin,
) {
    val bukkit2Geary = BukkitEntity2Geary()
    val uuid2entity = UUID2GearyMap()

    fun inject() {
        DI.add(this)
    }

    fun start() {
        uuid2entity.track()
        bukkit2Geary.track()
    }
}
