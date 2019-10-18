package com.carlosgub.coroutines.features.books.data.repository

import com.carlosgub.coroutines.features.books.data.datasource.rest.PostRestDataStore
import com.carlosgub.coroutines.features.books.data.mapper.PostDataMapper
import com.carlosgub.coroutines.features.books.domain.model.PostEntity
import com.carlosgub.coroutines.features.books.domain.repository.PostRepository

class PostRepositoryImpl(
    private val postRestDataStore: PostRestDataStore
) : PostRepository {

    private val mPostDataMapper by lazy { PostDataMapper() }

    override suspend fun getPosts(): List<PostEntity> =
        mPostDataMapper.map(postRestDataStore.getPosts())

    override suspend fun getPostById(id: String): PostEntity =
        mPostDataMapper.map(postRestDataStore.getPostById(id = id))

}