package com.carlosgub.coroutines.features.books.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.carlosgub.coroutines.core.platform.BaseViewModel
import com.carlosgub.coroutines.core.utils.io
import com.carlosgub.coroutines.core.utils.ui
import com.carlosgub.coroutines.features.books.domain.interactor.GetPostByIdInteractor
import com.carlosgub.coroutines.features.books.presentation.model.mapper.PostVMMapper
import com.carlosgub.coroutines.features.books.presentation.viewmodel.state.PostVS
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class PostViewModel(
    private val getPostByIdInteractor: GetPostByIdInteractor
) : BaseViewModel() {

    val viewState: LiveData<PostVS> get() = mViewState
    private val mViewState = MutableLiveData<PostVS>()

    private val mPostVMMapper by lazy { PostVMMapper() }

    fun getPostByID(id: String) {
        viewModelScope.launch {
            mViewState.value = PostVS.ShowLoader(true)
            try {
                io {
                    getPostByIdInteractor.execute(GetPostByIdInteractor.Params(id = id))
                        .collect {
                            ui {
                                mViewState.value = PostVS.Post(mPostVMMapper.map(it))
                            }
                        }
                }
            } catch (e: Exception) {
                ui {
                    mViewState.value = PostVS.Error(e.message)
                }
            }
            mViewState.value = PostVS.ShowLoader(false)
        }
    }
}