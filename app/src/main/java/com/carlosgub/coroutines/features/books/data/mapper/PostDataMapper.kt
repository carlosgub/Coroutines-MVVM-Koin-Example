package com.carlosgub.coroutines.features.books.data.mapper

import com.carlosgub.coroutines.core.mapper.Mapper
import com.carlosgub.coroutines.features.books.data.datasource.rest.response.PostResponseData
import com.carlosgub.coroutines.features.books.domain.model.PostEntity

class PostDataMapper : Mapper<PostResponseData, PostEntity> {
    override fun map(origin: PostResponseData) =
        PostEntity(
            userId = origin.userId,
            id = origin.id,
            title = origin.title,
            body = origin.body
        )
}