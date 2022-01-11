package com.mineinabyss.geary.minecraft.components

import com.mineinabyss.idofront.messaging.color
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import net.kyori.adventure.bossbar.BossBar
import net.kyori.adventure.text.Component
import java.util.*

/**
 * > geary:bossbar
 *
 * Holds properties of a boss bar (colour, style, range)
 */
@Serializable
@SerialName("geary:bossbar")
class DisplayBossBar(
    val title: String,
    val color: BossBar.Color,
    val style: BossBar.Overlay,
    val flags: Set<BossBar.Flag> = setOf(),
    val range: Double,
) {
    @Transient
    val bossBar: BossBar = BossBar.bossBar(Component.text(title.color()), 1f, color, style, flags)

    @Transient
    val playersInRange: MutableSet<UUID> = mutableSetOf()
}
