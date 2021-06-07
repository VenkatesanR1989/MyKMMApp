package com.example.shared

import com.example.shared.Grain
import kotlinx.serialization.Serializable

@Serializable
data class GrainList(
    val entries: List<Grain>
)