package com.carlosgub.coroutines.features.books.data.datasource.rest.interfaces
import com.carlosgub.coroutines.features.books.data.datasource.rest.response.PostResponseData
import retrofit2.http.GET
import retrofit2.http.Path

interface IPostApiClient {
    @GET("/posts")
    suspend fun getPosts(): List<PostResponseData>

    @GET("/posts/{id}")
    suspend fun getPostById(@Path("id") id:String): PostResponseData
}