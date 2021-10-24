package com.mineinabyss.geary.minecraft.components

import com.mineinabyss.geary.ecs.api.autoscan.AutoscanComponent
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("geary:deny_stacking")
@AutoscanComponent
class DenyStacking {
}