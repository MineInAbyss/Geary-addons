package com.mineinabyss.geary.minecraft.actions

import com.mineinabyss.geary.ecs.api.entities.GearyEntity
import com.mineinabyss.geary.ecs.components.CooldownManager
import com.mineinabyss.idofront.time.TimeSpan
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * > geary:cooldown
 *
 * @param length The length of this cooldown.
 * @param key The name of this cooldown, will be used as the key on this entity's [CooldownManager].
 * Defaults to the hashCode of [run].
 */
@Serializable
@SerialName("geary:cooldown")
class CooldownConfig(
    val length: TimeSpan,
    val key: String,
)

/**
 * An action that will start a cooldown, storing it in the entity's [CooldownManager] component.
 *
 * The action will only succeed once the cooldown is over, which will then run a list of actions. If none of the
 * child actions succeed, the cooldown is not started.
 */
inline fun GearyEntity.withCooldown(
    conf: CooldownConfig,
    run: GearyEntity.() -> Boolean
): Boolean {
    val cooldowns = getOrSetPersisting { CooldownManager() }

    return cooldowns.onCooldownIf(conf.key, conf.length) {
        run()
    }
}
