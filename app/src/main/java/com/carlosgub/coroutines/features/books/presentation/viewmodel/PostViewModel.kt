package com.carlosgub.coroutines.features.books.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.carlosgub.coroutines.core.interactor.Interactor
import com.carlosgub.coroutines.core.platform.BaseViewModel
import com.carlosgub.coroutines.features.books.domain.interactor.GetPostsInteractor
import com.carlosgub.coroutines.features.books.presentation.model.mapper.PostVMMapper
import com.carlosgub.coroutines.features.books.presentation.viewmodel.state.PostVS
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

class PostViewModel(
    val getPostsInteractor: GetPostsInteractor
) : BaseViewModel() {

    private val mPostVMMapper by lazy { PostVMMapper() }

    @ExperimentalCoroutinesApi
    fun getPosts():LiveData<PostVS> =
        getPostsInteractor.execute(Interactor.None)
            .map {
                PostVS.AddPost(mPostVMMapper.map(it))
            }
            .catch {
                PostVS.Error(it.message)
            }
            .asLiveData()


}