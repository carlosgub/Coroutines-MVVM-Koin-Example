package com.carlosgub.coroutines.features.books.presentation.viewmodel.viewstate

import com.carlosgub.coroutines.features.books.presentation.model.PostVM

sealed class PostVS {
    class Loading(val show: Boolean) : PostVS()
    class AddPost(val post: PostVM) : PostVS()
    class Error(val error: String?) : PostVS()
}