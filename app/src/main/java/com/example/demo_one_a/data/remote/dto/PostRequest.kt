package com.example.demo_one_a.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class PostRequest(
    val id: Int,
    val title: String,
    val artist: String,
    val chain: String,
    val price: String,
    val imageUrl: String,
    val createdAt: String,
    val address: String,
    val description: String
)
