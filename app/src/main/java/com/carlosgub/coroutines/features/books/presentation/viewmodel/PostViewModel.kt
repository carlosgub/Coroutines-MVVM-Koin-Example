package com.carlosgub.coroutines.features.books.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.carlosgub.coroutines.core.interactor.Interactor
import com.carlosgub.coroutines.core.platform.BaseViewModel
import com.carlosgub.coroutines.features.books.domain.interactor.GetPostsInteractor
import com.carlosgub.coroutines.features.books.presentation.model.PostVM
import com.carlosgub.coroutines.features.books.presentation.model.mapper.PostVMMapper
import kotlinx.coroutines.flow.*

class PostViewModel(
    private val getPostsInteractor: GetPostsInteractor
) : BaseViewModel() {

    private val mPostVMMapper by lazy { PostVMMapper() }


    val books: LiveData<PostVM> = liveData {
        getPostsInteractor.execute(Interactor.None)
            .buffer()
            .onStart {

            }
            .map { mPostVMMapper.map(it) }
            .onCompletion{

            }
            .catch { throw Throwable(it) }
            .asLiveData()
    }

}