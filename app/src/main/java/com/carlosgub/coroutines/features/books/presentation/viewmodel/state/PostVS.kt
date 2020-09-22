package com.carlosgub.coroutines.features.books.presentation.viewmodel.state

import com.carlosgub.coroutines.features.books.presentation.model.PostVM

sealed class PostVS {
    class Post(val postVM: PostVM):PostVS()
    class Error(val message:String?):PostVS()
    class ShowLoader(val showLoader:Boolean):PostVS()
}