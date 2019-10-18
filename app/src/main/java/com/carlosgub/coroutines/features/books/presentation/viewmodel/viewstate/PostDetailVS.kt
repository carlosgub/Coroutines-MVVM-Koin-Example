package com.carlosgub.coroutines.features.books.presentation.viewmodel.viewstate

import com.carlosgub.coroutines.features.books.presentation.model.PostVM

sealed class PostDetailVS {
    class Loading(val show: Boolean) : PostDetailVS()
    class GetPost(val post: PostVM) : PostDetailVS()
}