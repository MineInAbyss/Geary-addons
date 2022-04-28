package com.mineinabyss.geary.papermc.systems

import com.mineinabyss.geary.annotations.AutoScan
import com.mineinabyss.geary.commons.components.CooldownManager
import com.mineinabyss.geary.datatypes.family.MutableFamilyOperations.Companion.has
import com.mineinabyss.geary.datatypes.family.family
import com.mineinabyss.geary.helpers.parent
import com.mineinabyss.geary.helpers.with
import com.mineinabyss.geary.systems.TickingSystem
import com.mineinabyss.geary.systems.accessors.TargetScope
import com.mineinabyss.geary.systems.accessors.get
import com.mineinabyss.looty.ecs.components.inventory.SlotType
import net.kyori.adventure.text.Component
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import kotlin.math.roundToInt
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

private val INTERVAL = 1.seconds

@AutoScan
class CooldownDisplaySystem : TickingSystem(interval = INTERVAL) {
    private val TargetScope.cooldownManager by get<CooldownManager>()
    private val TargetScope.held by family { has<SlotType.Held>() }

    override fun TargetScope.tick() {
        entity.parent?.with { player: Player ->
            player.sendActionBar(Component.text(cooldownManager.incompleteCooldowns.entries.joinToString("\n") { (key, cooldown) ->
                val length = cooldown.length.milliseconds
                val timeLeft = (cooldown.endTime - System.currentTimeMillis()).milliseconds
                val squaresLeft =
                    if (timeLeft < INTERVAL) 0 else (timeLeft / length * displayLength).roundToInt()

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
                    if (timeLeft < INTERVAL) append(
                        ChatColor.GREEN,
                        " [✔]"
                    ) else append(ChatColor.GRAY, " [$timeLeft]")
                }
            }))
        }
    }

    companion object {
        private const val displayLength = 10
        private const val displayChar = '■'
    }
}
