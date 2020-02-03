package com.carlosgub.coroutines.features.books.data.datasource.rest

import com.carlosgub.coroutines.features.books.data.datasource.rest.response.PostResponseData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PostRestDataStore {

    fun getPosts(): Flow<PostResponseData> = flow {
        PostApiClient.getApiClient().getPosts().forEach {
            emit(it)
        }
    }


    fun getPostById(id: String): Flow<PostResponseData> = flow {
        emit(PostApiClient.getApiClient().getPostById(id = id))
    }

}