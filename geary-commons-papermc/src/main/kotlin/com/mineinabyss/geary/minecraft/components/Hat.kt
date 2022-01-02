package com.mineinabyss.geary.minecraft.components

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bukkit.Sound

@Serializable
@SerialName("geary:hat")
class Hat(
    val sound: Sound = Sound.ITEM_ARMOR_EQUIP_NETHERITE
)
