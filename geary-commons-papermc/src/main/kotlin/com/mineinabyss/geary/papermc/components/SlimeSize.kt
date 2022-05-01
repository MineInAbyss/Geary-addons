package com.mineinabyss.geary.papermc.components

import com.mineinabyss.idofront.serialization.IntRangeSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@JvmInline
@Serializable
@SerialName("geary:set_slime_size")
value class SlimeSize(@Serializable(with = IntRangeSerializer::class) val size: IntRange)