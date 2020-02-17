package com.carlosgub.coroutines.features.books.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.carlosgub.coroutines.core.interactor.Interactor
import com.carlosgub.coroutines.core.platform.BaseViewModel
import com.carlosgub.coroutines.core.utils.io
import com.carlosgub.coroutines.core.utils.ui
import com.carlosgub.coroutines.features.books.domain.interactor.GetPostsInteractor
import com.carlosgub.coroutines.features.books.presentation.model.PostVM
import com.carlosgub.coroutines.features.books.presentation.model.mapper.PostVMMapper
import com.carlosgub.coroutines.features.books.presentation.viewmodel.state.PostVS
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.lang.Exception

class PostViewModel(
    private val getPostsInteractor: GetPostsInteractor
) : BaseViewModel() {

    val data: LiveData<PostVM>
        get() = _data
    val _data = MutableLiveData<PostVM>()

    val viewState: LiveData<PostVS>
        get() = _viewState
    private val _viewState = MutableLiveData<PostVS>()

    private val postVMMapper by lazy { PostVMMapper() }

    fun getPosts() {
        viewModelScope.launch {
            try {
                io {
                    getPostsInteractor.execute(Interactor.None)
                        .collect {
                            ui { _data.value = postVMMapper.map(it) }
                        }
                }
            } catch (e: Exception) {
                ui { _viewState.value = PostVS.Error(e.message) }
            }
        }
    }
}