package com.carlosgub.coroutines.features.books.presentation.model.mapper

import com.carlosgub.coroutines.core.mapper.Mapper
import com.carlosgub.coroutines.features.books.domain.model.PostEntity
import com.carlosgub.coroutines.features.books.presentation.model.PostVM

class PostVMMapper : Mapper<PostEntity, PostVM> {
    override fun map(origin: PostEntity) =
        PostVM(
            userId = origin.userId,
            id = origin.id,
            title = origin.title,
            body = origin.body
        )
}