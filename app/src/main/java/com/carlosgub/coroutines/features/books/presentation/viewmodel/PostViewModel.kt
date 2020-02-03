package com.carlosgub.coroutines.features.books.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.carlosgub.coroutines.core.interactor.Interactor
import com.carlosgub.coroutines.core.platform.BaseViewModel
import com.carlosgub.coroutines.features.books.domain.interactor.GetPostsInteractor
import com.carlosgub.coroutines.features.books.presentation.model.mapper.PostVMMapper
import com.carlosgub.coroutines.features.books.presentation.viewmodel.viewstate.PostVS
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

class PostViewModel(
    private val getPostsInteractor: GetPostsInteractor
) : BaseViewModel() {

    val state = MutableLiveData<PostVS>()

    private val mPostVMMapper by lazy { PostVMMapper() }

    @ExperimentalCoroutinesApi
    fun getPosts() {
        getPostsInteractor.execute(Interactor.None)
            .buffer()
            .onEach { state.value = PostVS.AddPost(mPostVMMapper.map(it)) }
            .catch { state.value = PostVS.Error(it.message) }
            .launchIn(viewModelScope)
    }
}