package com.mineinabyss.geary.minecraft.components

import com.mineinabyss.idofront.messaging.color
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import org.bukkit.Bukkit
import org.bukkit.boss.BarColor
import org.bukkit.boss.BarStyle
import org.bukkit.boss.BossBar
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
    val color: BarColor,
    val style: BarStyle,
    val range: Double,
) {
    @Transient
    val bossBar: BossBar = Bukkit.createBossBar(title.color(), color, style)

    @Transient
    val playersInRange: MutableSet<UUID> = mutableSetOf()
}
