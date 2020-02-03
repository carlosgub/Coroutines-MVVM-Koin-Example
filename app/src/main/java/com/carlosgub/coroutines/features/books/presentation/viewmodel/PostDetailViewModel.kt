package com.carlosgub.coroutines.features.books.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.carlosgub.coroutines.core.platform.BaseViewModel
import com.carlosgub.coroutines.features.books.domain.interactor.GetPostByIdInteractor
import com.carlosgub.coroutines.features.books.presentation.model.mapper.PostVMMapper
import com.carlosgub.coroutines.features.books.presentation.viewmodel.viewstate.PostDetailVS
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class PostDetailViewModel(
    private val getPostByIdInteractor: GetPostByIdInteractor
) : BaseViewModel() {

    private val mPostVMMapper by lazy { PostVMMapper() }

    val state = MutableLiveData<PostDetailVS>()

    fun getPostById(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            state.postValue(PostDetailVS.Loading(true))
            getPostByIdInteractor.execute(GetPostByIdInteractor.Params(id = id)).map {
                state.postValue(PostDetailVS.GetPost(mPostVMMapper.map(it)))
            }
            state.postValue(PostDetailVS.Loading(false))
        }
    }
}