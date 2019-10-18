package com.carlosgub.coroutines.features.books.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.carlosgub.coroutines.core.interactor.Interactor
import com.carlosgub.coroutines.core.platform.BaseViewModel
import com.carlosgub.coroutines.features.books.domain.interactor.GetPostByIdInteractor
import com.carlosgub.coroutines.features.books.domain.interactor.GetPostsInteractor
import com.carlosgub.coroutines.features.books.presentation.model.PostVM
import com.carlosgub.coroutines.features.books.presentation.model.mapper.PostVMMapper
import com.carlosgub.coroutines.features.books.presentation.viewmodel.viewstate.PostDetailVS
import kotlinx.coroutines.*

class PostDetailViewModel(
    private val getPostByIdInteractor: GetPostByIdInteractor
) : BaseViewModel() {

    private val mPostVMMapper by lazy { PostVMMapper() }

    val state = MutableLiveData<PostDetailVS>()

    suspend fun getPostById(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            state.postValue(PostDetailVS.Loading(true))
            val post =
                mPostVMMapper.map(getPostByIdInteractor.execute(GetPostByIdInteractor.Params(id = id)))
            state.postValue(PostDetailVS.GetPost(post))
            state.postValue(PostDetailVS.Loading(false))
        }
    }
}