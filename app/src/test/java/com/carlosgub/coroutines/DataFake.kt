package com.carlosgub.coroutines

import com.carlosgub.coroutines.features.books.domain.model.PostEntity

object DataFake {
    val dataEmpty = emptyList<PostEntity>()
    val dataList = listOf<PostEntity>(
        PostEntity(1, 1, "Harry Potter 1", "First book"),
        PostEntity(2, 2, "Harry Potter 2", "Second book"),
        PostEntity(3, 3, "Harry Potter 3", "Third book")
    )
}