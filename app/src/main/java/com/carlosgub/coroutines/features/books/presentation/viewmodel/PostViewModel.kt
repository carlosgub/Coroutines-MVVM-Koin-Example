package com.carlosgub.coroutines.features.books.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.carlosgub.coroutines.core.interactor.Interactor
import com.carlosgub.coroutines.core.platform.BaseViewModel
import com.carlosgub.coroutines.features.books.domain.interactor.GetPostsInteractor
import com.carlosgub.coroutines.features.books.presentation.model.PostVM
import com.carlosgub.coroutines.features.books.presentation.model.mapper.PostVMMapper
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PostViewModel(
    private val getPostsInteractor: GetPostsInteractor
) : BaseViewModel() {

    private val postsDeferred = CompletableDeferred<List<PostVM>>()

    init {
        viewModelScope.launch(Dispatchers.IO){
            val posts = mPostVMMapper.map(getPostsInteractor.execute(Interactor.None))
            postsDeferred.complete(posts)
        }

        /*
        * Experimental Para llamadas paralelas
        *
        * viewModelScope.launch(Dispatchers.IO){
            val posts = async { mPostVMMapper.map(getPostsInteractor.execute(Interactor.None)) }
            val posts2 = async { mPostVMMapper.map(getPostsInteractor.execute(Interactor.None)) }
            postsDeferred.complete(posts.await() + posts2.await())
        }
        */
    }

    private val mPostVMMapper by lazy { PostVMMapper() }

    suspend fun getPosts(): List<PostVM> = postsDeferred.await()
}