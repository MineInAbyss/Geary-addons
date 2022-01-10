package com.mineinabyss.geary.minecraft.systems

import com.mineinabyss.geary.ecs.accessors.TargetScope
import com.mineinabyss.geary.ecs.accessors.get
import com.mineinabyss.geary.ecs.api.autoscan.AutoScan
import com.mineinabyss.geary.ecs.api.entities.with
import com.mineinabyss.geary.ecs.api.systems.TickingSystem
import com.mineinabyss.geary.ecs.components.CooldownManager
import com.mineinabyss.geary.ecs.entities.parent
import com.mineinabyss.looty.ecs.components.inventory.SlotType
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import kotlin.math.roundToInt
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.DurationUnit

private const val INTERVAL = 1L

@AutoScan
class CooldownDisplaySystem : TickingSystem(interval = INTERVAL) {
    init {
        has<SlotType.Held>()
    }

    private val TargetScope.cooldownManager by get<CooldownManager>()

    override fun TargetScope.tick() {
        entity.parent?.with { player: Player ->
            player.sendActionBar(cooldownManager.incompleteCooldowns.entries.joinToString("\n") { (key, cooldown) ->
                val length = cooldown.length.milliseconds
                val timeLeft = (cooldown.endTime - System.currentTimeMillis()).milliseconds
                val squaresLeft =
                    if (timeLeft.toDouble(DurationUnit.SECONDS) * 20 < INTERVAL) 0 else (timeLeft / length * displayLength).roundToInt()

                buildString {
                    append("$key ")
                    append(ChatColor.GREEN)
                    repeat(displayLength - squaresLeft) {
                        append(displayChar)
                    }
                    append(ChatColor.RED)
                    repeat(squaresLeft) {
                        append(displayChar)
                    }
                    if (timeLeft.toDouble(DurationUnit.MILLISECONDS) < INTERVAL * 1000 / 20) append(
                        ChatColor.GREEN,
                        " [✔]"
                    ) else append(ChatColor.GRAY, " [$timeLeft]")
                }
            })
        }
    }

    companion object {
        private const val displayLength = 10
        private const val displayChar = '■'
    }
}
