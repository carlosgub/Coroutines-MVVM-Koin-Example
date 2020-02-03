package com.carlosgub.coroutines.features.books.domain.interactor

import com.carlosgub.coroutines.core.interactor.Interactor
import com.carlosgub.coroutines.features.books.domain.model.PostEntity
import com.carlosgub.coroutines.features.books.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow

class GetPostsInteractor(
    private val postRepository: PostRepository
) : Interactor<Interactor.None, Flow<PostEntity>> {

    override fun execute(params: Interactor.None): Flow<PostEntity> {
        return postRepository.getPosts()
    }
}