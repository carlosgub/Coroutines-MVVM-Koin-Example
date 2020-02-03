package com.carlosgub.coroutines

import com.carlosgub.coroutines.core.interactor.Interactor
import com.carlosgub.coroutines.features.books.domain.model.PostEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow

class FakePostInteractor : Interactor<Interactor.None, Flow<PostEntity>> {

    override fun execute(params: Interactor.None): Flow<PostEntity> =
        arrayListOf<PostEntity>(
            PostEntity(1, 1, "Harry Potter 1", "First book"),
            PostEntity(2, 2, "Harry Potter 2", "Second book"),
            PostEntity(3, 3, "Harry Potter 3", "Third book")
        ).asFlow()
}
