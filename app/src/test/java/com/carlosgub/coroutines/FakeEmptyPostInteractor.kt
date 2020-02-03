package com.carlosgub.coroutines

import com.carlosgub.coroutines.core.interactor.Interactor
import com.carlosgub.coroutines.features.books.domain.model.PostEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow

class FakeEmptyPostInteractor : Interactor<Interactor.None, Flow<PostEntity>> {

    override fun execute(params: Interactor.None): Flow<PostEntity> = emptyArray<PostEntity>().asFlow()

}
