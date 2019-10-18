package com.carlosgub.coroutines.features.books.domain.interactor

import com.carlosgub.coroutines.core.interactor.Interactor
import com.carlosgub.coroutines.features.books.domain.model.PostEntity
import com.carlosgub.coroutines.features.books.domain.repository.PostRepository

class GetPostsInteractor(
    private val postRepository: PostRepository
) : Interactor<Interactor.None, List<PostEntity>> {

    override suspend fun execute(params: Interactor.None): List<PostEntity> {
        return postRepository.getPosts()
    }
}