package com.mineinabyss.geary.commons.components

import com.mineinabyss.idofront.serialization.DurationSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlin.time.Duration

/**
 * > geary:expiry
 *
 * States something should expire after [duration] time since the component's creation. Commonly used in a relation
 * with other components to remove those components later.
 */
@Serializable
@SerialName("geary:expiry")
public class Expiry(
    @Serializable(with = DurationSerializer::class)
    public val duration: Duration
) {
    @Transient
    public val endTime: Long = System.currentTimeMillis() + duration.inWholeMilliseconds

    public fun timeOver(): Boolean = System.currentTimeMillis() > endTime
    public operator fun component1(): Long = endTime
}
