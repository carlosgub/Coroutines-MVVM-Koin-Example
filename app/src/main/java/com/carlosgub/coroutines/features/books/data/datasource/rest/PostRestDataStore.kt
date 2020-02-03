package com.carlosgub.coroutines.features.books.data.datasource.rest

import com.carlosgub.coroutines.features.books.data.datasource.rest.response.PostResponseData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class PostRestDataStore {

    fun getPosts(): Flow<PostResponseData> =
        flow {
            withContext(Dispatchers.IO) {
                PostApiClient.getApiClient().getPosts().forEach {
                    emit(it)
                }
            }
        }


    fun getPostById(id: String): Flow<PostResponseData> = flow {

        withContext(Dispatchers.IO) {
            emit(PostApiClient.getApiClient().getPostById(id = id))
        }
    }

}