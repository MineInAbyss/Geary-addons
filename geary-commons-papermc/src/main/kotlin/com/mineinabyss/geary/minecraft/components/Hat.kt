package com.mineinabyss.geary.minecraft.components

import com.mineinabyss.geary.ecs.api.autoscan.AutoscanComponent
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bukkit.Sound

@Serializable
@SerialName("geary:hat")
@AutoscanComponent
class Hat(
    val sound: Sound = Sound.ITEM_ARMOR_EQUIP_NETHERITE
)
