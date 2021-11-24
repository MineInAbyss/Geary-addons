package com.mineinabyss.geary.minecraft.components

import com.mineinabyss.idofront.serialization.UUIDSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
@SerialName("geary:soulbound")
class Soulbound(
    var owner: @Serializable(with = UUIDSerializer::class) UUID,
    var ownerName: String
)
