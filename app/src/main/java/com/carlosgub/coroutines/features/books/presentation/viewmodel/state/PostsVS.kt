package com.carlosgub.coroutines.features.books.presentation.viewmodel.state

import com.carlosgub.coroutines.features.books.presentation.model.PostVM

sealed class PostsVS {
    class AddPost(val postVM: PostVM):PostsVS()
    class Error(val message:String?):PostsVS()
    class ShowLoader(val showLoader:Boolean):PostsVS()
}