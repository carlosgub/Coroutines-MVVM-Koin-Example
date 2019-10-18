package com.carlosgub.coroutines.features.books.presentation.viewmodel

import com.carlosgub.coroutines.core.interactor.Interactor
import com.carlosgub.coroutines.core.platform.BaseViewModel
import com.carlosgub.coroutines.features.books.domain.interactor.GetPostByIdInteractor
import com.carlosgub.coroutines.features.books.domain.interactor.GetPostsInteractor
import com.carlosgub.coroutines.features.books.presentation.model.PostVM
import com.carlosgub.coroutines.features.books.presentation.model.mapper.PostVMMapper

class PostDetailViewModel(
    private val getPostByIdInteractor: GetPostByIdInteractor
) : BaseViewModel() {

    private val mPostVMMapper by lazy { PostVMMapper() }

    suspend fun getPostById(id:String): PostVM =
        mPostVMMapper.map(getPostByIdInteractor.execute(GetPostByIdInteractor.Params(id = id)))
}