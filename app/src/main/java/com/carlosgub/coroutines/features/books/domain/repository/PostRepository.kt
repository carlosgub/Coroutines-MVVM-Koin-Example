package com.carlosgub.coroutines.features.books.domain.repository

import com.carlosgub.coroutines.features.books.domain.model.PostEntity

interface PostRepository {
    suspend fun getPosts(): List<PostEntity>
    suspend fun getPostById(id: String): PostEntity
}