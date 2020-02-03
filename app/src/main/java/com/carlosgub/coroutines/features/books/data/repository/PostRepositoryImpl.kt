package com.carlosgub.coroutines.features.books.data.repository

import com.carlosgub.coroutines.features.books.data.datasource.rest.PostRestDataStore
import com.carlosgub.coroutines.features.books.data.mapper.PostDataMapper
import com.carlosgub.coroutines.features.books.domain.model.PostEntity
import com.carlosgub.coroutines.features.books.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform

class PostRepositoryImpl(
    private val postRestDataStore: PostRestDataStore
) : PostRepository {

    private val mPostDataMapper by lazy { PostDataMapper() }

    override fun getPosts(): Flow<PostEntity> =
        postRestDataStore.getPosts().transform{
            mPostDataMapper.map(it)
        }

    override fun getPostById(id: String): Flow<PostEntity> =
        postRestDataStore.getPostById(id = id).transform {
            mPostDataMapper.map(it)
        }
}
