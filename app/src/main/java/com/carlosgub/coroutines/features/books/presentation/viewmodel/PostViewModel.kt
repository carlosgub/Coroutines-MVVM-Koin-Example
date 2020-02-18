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

    private val _data = MutableLiveData<List<PostVM>>()
    val data: LiveData<List<PostVM>>
        get() = _data

    val viewState: LiveData<PostVS>
        get() = _viewState
    private val _viewState = MutableLiveData<PostVS>()

    private val postVMMapper by lazy { PostVMMapper() }

    fun getPosts() {
        viewModelScope.launch {
            try {
                io {
                    val list =
                        getPostsInteractor.execute(Interactor.None).toList().map(postVMMapper::map)
                    ui {
                        _data.value = list
                    }
                }
            } catch (e: Exception) {
                ui {
                    _viewState.value = PostVS.Error(e.message)
                }
            }
        }
    }
}