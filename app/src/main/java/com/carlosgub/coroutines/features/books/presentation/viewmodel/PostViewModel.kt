package com.carlosgub.coroutines.features.books.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import com.carlosgub.coroutines.core.interactor.Interactor
import com.carlosgub.coroutines.core.platform.BaseViewModel
import com.carlosgub.coroutines.features.books.domain.interactor.GetPostsInteractor
import com.carlosgub.coroutines.features.books.presentation.model.PostVM
import com.carlosgub.coroutines.features.books.presentation.model.mapper.PostVMMapper
import com.carlosgub.coroutines.features.books.presentation.viewmodel.viewstate.PostVS
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class PostViewModel(
    private val getPostsInteractor: GetPostsInteractor
) : BaseViewModel() {

    val state = MutableLiveData<PostVS>()

    private val mPostVMMapper by lazy { PostVMMapper() }

    @ExperimentalCoroutinesApi
    fun getPosts() : Flow<PostVM> =
        getPostsInteractor.execute(Interactor.None)
            .buffer()
            .map { mPostVMMapper.map(it) }
            .catch { throw Throwable(it)  }

}