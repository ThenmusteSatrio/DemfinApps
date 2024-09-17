package com.example.demo_one_a.data.remote

object HttpRoutes {
    private const val BASE_URL = "http://192.168.1.12:3000"
    const val POSTS = "$BASE_URL/posts"
    const val REGISTER = "${BASE_URL}/users"
    const val NFTSGENERAL = "${BASE_URL}/nfts"
    const val ISREGISTERED = "${BASE_URL}/users/"
}