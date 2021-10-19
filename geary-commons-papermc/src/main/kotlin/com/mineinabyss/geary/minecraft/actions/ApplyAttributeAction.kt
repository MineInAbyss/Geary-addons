package com.mineinabyss.geary.minecraft.actions

import com.mineinabyss.geary.ecs.api.actions.GearyAction
import com.mineinabyss.geary.ecs.api.entities.GearyEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bukkit.attribute.Attribute
import org.bukkit.entity.Entity
import org.bukkit.entity.LivingEntity

/**
 * Modifies a specified attribute by a specifies amount.
 *
 * @param attribute The attribute to modify.
 * @param amplifier Specifies amount to modify attribute by.
 */
@Serializable
@SerialName("geary:apply_attribute")
class ApplyAttributeAction(
    private val attribute: Attribute,
    private val amplifier: Double = 1.0,
) : GearyAction() {
    private val GearyEntity.entity by get<Entity>()

    override fun GearyEntity.run(): Boolean {
        val player = entity as? LivingEntity ?: return false
        player.getAttribute(attribute)?.setBaseValue(amplifier) ?: return false
        return true
    }
}