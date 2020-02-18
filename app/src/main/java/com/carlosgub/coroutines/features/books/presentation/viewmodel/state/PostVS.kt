package com.carlosgub.coroutines.features.books.presentation.viewmodel.state

import com.carlosgub.coroutines.features.books.presentation.model.PostVM

sealed class PostVS {
    class Error(val message:String?):PostVS()
}