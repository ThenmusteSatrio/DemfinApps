package com.example.demo_one_a.data.remote

import android.content.Context
import android.net.Uri
import com.example.demo_one_a.data.remote.dto.PostResponse
import com.example.demo_one_a.data.remote.dto.PostRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.client.statement.readText
import io.ktor.client.utils.EmptyContent.contentType
import io.ktor.client.utils.EmptyContent.headers
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.content.PartData
import io.ktor.http.contentType
import io.ktor.util.InternalAPI
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import java.io.File


class PostsServiceImpl(private val client: HttpClient) : PostsService {
    override suspend fun getPosts(): List<PostResponse> {
        return try {
            client.get(HttpRoutes.POSTS).body<List<PostResponse>>()
        } catch (e: RedirectResponseException) {
            println("Error: ${e.response.status.description}")
            emptyList()
        } catch (e: Exception) {
            println("Error: ${e.message}")
            emptyList()
        }
    }

    override suspend fun createPost(postRequest: PostRequest): PostResponse? {
        return try {
            client.post(HttpRoutes.POSTS) {
                contentType(ContentType.Application.Json)
                setBody(postRequest)
            }.body<PostResponse>()
        } catch (e: RedirectResponseException) {
            println("Error: ${e.response.status.description}")
            null
        } catch (e: Exception) {
            println("Error: ${e.message}")
            null
        }
    }

    override suspend fun isRegistered(
        metamaskAddress: String
    ): HttpResponse? {
        return try {
            client.get("${HttpRoutes.ISREGISTERED}$metamaskAddress")
        }catch (e: RedirectResponseException) {
            println("Error: ${e.response.status.description}")
            null
        } catch (e: Exception) {
            println("Error: ${e.message}")
            null
        }
    }

    override suspend fun uploadImage(imageFile: File, username: String, metamaskAddress: String): HttpResponse? {
        return try {
            client.submitFormWithBinaryData(
                url = HttpRoutes.REGISTER,
                formData = formData {
                    append("username", username)
                    append("address", metamaskAddress)
                    append("profileImage", imageFile.readBytes(), Headers.build {
                        append(HttpHeaders.ContentType, ContentType.Image.Any.toString())
                        append(HttpHeaders.ContentDisposition, "filename=\"${imageFile.name}\"")
                    })
                }
            )

        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override suspend fun uploadNFTs(
        imageFile: File,
        name: String,
        artist: String,
        chain: String,
        price: String,
        created_at: String,
        description: String,
        address: String,
    ): HttpResponse? {
        return try {
            client.submitFormWithBinaryData(
                url = HttpRoutes.NFTSGENERAL,
                formData = formData {
                    append("name", name)
                    append("artist", artist)
                    append("chain", chain)
                    append("price", price)
                    append("created_at", created_at)
                    append("description", created_at)
                    append("address", address)
                    append("nftsImage", imageFile.readBytes(), Headers.build {
                        append(HttpHeaders.ContentType, ContentType.Image.Any.toString())
                        append(HttpHeaders.ContentDisposition, "filename=\"${imageFile.name}\"")
                    })
                }
            )

        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override suspend fun getNFTs(): List<PostResponse> {
        return try {
            client.get(HttpRoutes.NFTSGENERAL).body<List<PostResponse>>()
        } catch (e: RedirectResponseException){
            println("Error: ${e.response.status.description}")
            emptyList()
        } catch (e: Exception){
            println("Error: ${e.message}")
            emptyList()
        }
    }




//    @Serializable
//    data class EthereumRequest(
//        val jsonrpc: String = "2.0",
//        val method: String = "eth_accounts",
//        val params: List<String> = emptyList(),
//        val id: Int = 1
//    )
//
//    @OptIn(InternalAPI::class)
//    override suspend fun getEthereumAccounts(): String {
//        val requestBody = EthereumRequest()
//        val response: HttpResponse = client.post("http://192.168.137.55:8545") {
//            contentType(ContentType.Application.Json)
//            setBody(requestBody)
//        }
//
//        val responseBody = response.bodyAsText()
//        client.close()
//        return responseBody
//    }

}
