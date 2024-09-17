package com.example.demo_one_a.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class Profile(
    val id: Int,
    val username: String,
    val address: String,
    val image: String
)
