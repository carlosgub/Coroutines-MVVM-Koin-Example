package com.carlosgub.coroutines.features.books.presentation.viewmodel

import com.carlosgub.coroutines.core.interactor.Interactor
import com.carlosgub.coroutines.core.platform.BaseViewModel
import com.carlosgub.coroutines.features.books.domain.interactor.GetPostsInteractor
import com.carlosgub.coroutines.features.books.presentation.model.PostVM
import com.carlosgub.coroutines.features.books.presentation.model.mapper.PostVMMapper

class PostViewModel(
    private val getPostsInteractor: GetPostsInteractor
) : BaseViewModel() {

    private val mPostVMMapper by lazy { PostVMMapper() }

    suspend fun getPosts(): List<PostVM> =
        mPostVMMapper.map(getPostsInteractor.execute(Interactor.None))
}