package com.mineinabyss.geary.papermc.features.display.bossbar

import com.mineinabyss.idofront.serialization.MiniMessageSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import net.kyori.adventure.bossbar.BossBar
import net.kyori.adventure.text.Component
import org.bukkit.attribute.Attribute
import org.bukkit.entity.LivingEntity
import java.util.*

/**
 * `geary:bossbar`
 * Displays a bossbar to nearby players when added to a bukkit entity.
 */
@Serializable
@SerialName("geary:bossbar")
class DisplayBossBar(
    @Serializable(with = MiniMessageSerializer::class)
    val title: Component,
    val color: BossBar.Color,
    val style: BossBar.Overlay,
    val flags: Set<BossBar.Flag> = setOf(),
    val range: Double,
) {
    @Transient
    val bossBar: BossBar = BossBar.bossBar(title, 1f, color, style, flags)

    @Transient
    val playersInRange: MutableSet<UUID> = mutableSetOf()


    fun setProgressToHealthOf(bukkit: LivingEntity) {
        bossBar.progress(
            (bukkit.health / (bukkit.getAttribute(Attribute.GENERIC_MAX_HEALTH)?.value ?: 20.0)).toFloat()
        )
    }
}
