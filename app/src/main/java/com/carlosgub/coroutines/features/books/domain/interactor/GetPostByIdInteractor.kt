package com.carlosgub.coroutines.features.books.domain.interactor

import com.carlosgub.coroutines.core.interactor.Interactor
import com.carlosgub.coroutines.features.books.domain.model.PostEntity
import com.carlosgub.coroutines.features.books.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow

class GetPostByIdInteractor(
    private val postRepository: PostRepository
) : Interactor<GetPostByIdInteractor.Params, Flow<PostEntity>> {

    override fun execute(params: Params): Flow<PostEntity> {
        return postRepository.getPostById(id = params.id)
    }

    data class Params(val id: String)
}