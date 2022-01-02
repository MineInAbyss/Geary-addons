package com.mineinabyss.geary.minecraft.actions

import com.mineinabyss.geary.ecs.api.entities.GearyEntity
import com.mineinabyss.idofront.typealiases.BukkitEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bukkit.attribute.Attribute
import org.bukkit.entity.LivingEntity

/**
 * @param attribute The attribute to modify.
 * @param amplifier Specifies amount to modify attribute by.
 */
@Serializable
@SerialName("geary:attribute")
class ApplicableAttribute(
    val attribute: Attribute,
    val amplifier: Double = 1.0
)

/**
 * Modifies a specified attribute by a specifies amount.
 */
fun GearyEntity.applyAttribute(
    attribute: ApplicableAttribute,
    entity: LivingEntity? = get<BukkitEntity>() as? LivingEntity
) {
    entity?.getAttribute(attribute.attribute)?.baseValue = attribute.amplifier
}
