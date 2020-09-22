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
import com.carlosgub.coroutines.features.books.presentation.viewmodel.state.PostsVS
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class PostsViewModel(
    private val getPostsInteractor: GetPostsInteractor
) : BaseViewModel() {

    val viewState: LiveData<PostsVS> get() = mViewState
    private val mViewState = MutableLiveData<PostsVS>()

    private val mPostVMMapper by lazy { PostVMMapper() }

    fun getPosts() {
        viewModelScope.launch {
            mViewState.value = PostsVS.ShowLoader(true)
            try {
                io {
                    getPostsInteractor.execute(Interactor.None)
                        .collect {
                            ui { mViewState.value = PostsVS.AddPost(mPostVMMapper.map(it)) }
                        }
                }
            } catch (e: Exception) {
                ui { mViewState.value = PostsVS.Error(e.message) }
            }
            mViewState.value = PostsVS.ShowLoader(false)
        }
    }
}