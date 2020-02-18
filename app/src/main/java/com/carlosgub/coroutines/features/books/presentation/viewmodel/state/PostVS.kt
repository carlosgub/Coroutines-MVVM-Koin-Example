package com.carlosgub.coroutines.features.books.presentation.viewmodel.state

sealed class PostVS {
    object Finish:PostVS()
    class Error(val message:String?):PostVS()
}