package com.carlosgub.coroutines.features.books.domain.interactor

import com.carlosgub.coroutines.core.interactor.Interactor
import com.carlosgub.coroutines.features.books.domain.model.PostEntity
import com.carlosgub.coroutines.features.books.domain.repository.PostRepository

class GetPostByIdInteractor(
    private val postRepository: PostRepository
) : Interactor<GetPostByIdInteractor.Params, PostEntity> {

    override suspend fun execute(params: Params): PostEntity {
        return postRepository.getPostById(id = params.id)
    }

    data class Params(val id: String)
}