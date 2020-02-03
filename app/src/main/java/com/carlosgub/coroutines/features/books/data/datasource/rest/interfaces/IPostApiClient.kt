package com.carlosgub.coroutines.features.books.data.datasource.rest.interfaces
import com.carlosgub.coroutines.features.books.data.datasource.rest.response.PostResponseData
import retrofit2.http.GET
import retrofit2.http.Path

interface IPostApiClient {
    @GET("/posts")
    fun getPosts(): List<PostResponseData>

    @GET("/posts/{id}")
    fun getPostById(@Path("id") id:String): PostResponseData
}