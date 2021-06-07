package com.example.shared

import kotlinx.serialization.Serializable

@Serializable
data class Grain(
    val id: Int,
    val name: String,
    val url: String?
)