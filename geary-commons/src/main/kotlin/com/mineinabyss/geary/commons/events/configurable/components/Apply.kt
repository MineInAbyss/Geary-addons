package com.mineinabyss.geary.commons.events.configurable.components

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("geary:apply_relation")
public object Apply

@JvmInline
@Serializable
@SerialName("geary:apply")
public value class ApplyBuilder(public val entityExpression: String)



