package com.example.demo_one_a.data.remote

import android.content.Context
import android.net.Uri
import com.example.demo_one_a.data.remote.dto.PostRequest
import com.example.demo_one_a.data.remote.dto.PostResponse
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.statement.HttpResponse
import io.ktor.serialization.kotlinx.json.json
import java.io.File

interface PostsService {
    suspend fun getPosts(): List<PostResponse>

    suspend fun createPost(postRequest: PostRequest): PostResponse?

//    suspend fun getEthereumAccounts(): String?

    suspend fun uploadImage(
        imageFile: File,
        username: String,
        metamaskAddress: String
    ): HttpResponse?

    suspend fun uploadNFTs(
        imageFile: File,
        name: String,
        artist: String,
        chain: String,
        price: String,
        created_at: String,
        description: String,
        address: String,
    ): HttpResponse?

    suspend fun getNFTs(): List<PostResponse>

    suspend fun isRegistered(
        metamaskAddress: String
    ): HttpResponse?

    companion object {
        fun create(): PostsService {
            return PostsServiceImpl(
                client = HttpClient(Android) {
                    install(Logging) {
                        level = LogLevel.ALL
                    }
                    install(ContentNegotiation) {
                        json()
                    }
                }
            )
        }
    }
}