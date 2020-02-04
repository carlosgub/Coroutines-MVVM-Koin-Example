package com.carlosgub.coroutines.features.books.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.carlosgub.coroutines.core.interactor.Interactor
import com.carlosgub.coroutines.core.platform.BaseViewModel
import com.carlosgub.coroutines.core.utils.io
import com.carlosgub.coroutines.core.utils.ui
import com.carlosgub.coroutines.features.books.domain.interactor.GetPostsInteractor
import com.carlosgub.coroutines.features.books.presentation.model.mapper.PostVMMapper
import com.carlosgub.coroutines.features.books.presentation.viewmodel.state.PostVS
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.lang.Exception

class PostViewModel(
    private val getPostsInteractor: GetPostsInteractor
) : BaseViewModel() {

    val viewState: LiveData<PostVS> get() = mViewState
    private val mViewState = MutableLiveData<PostVS>()

    private val mPostVMMapper by lazy { PostVMMapper() }

    fun getPosts() {
        viewModelScope.launch {
            try {
                io {
                    getPostsInteractor.execute(Interactor.None)
                        .collect {
                            ui { mViewState.value = PostVS.AddPost(mPostVMMapper.map(it)) }
                        }
                }
            } catch (e: Exception) {
                ui { mViewState.value = PostVS.Error(e.message) }
            }
        }
    }
}