package com.carlosgub.coroutines.features.books.data.datasource.rest

import com.carlosgub.coroutines.features.books.data.datasource.rest.response.PostResponseData

class PostRestDataStore {

    suspend fun getPosts(): List<PostResponseData> =
        PostApiClient.getApiClient().getPosts()


    suspend fun getPostById(id: String): PostResponseData =
        PostApiClient.getApiClient().getPostById(id = id)
}