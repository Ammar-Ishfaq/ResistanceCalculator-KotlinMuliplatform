package com.ammar.resistorassistant.data

import kotlinx.serialization.Serializable

@Serializable
data class ResistorObject(
    val id: Int,
    val name: String,
    val image: String,
    val description: String
)
