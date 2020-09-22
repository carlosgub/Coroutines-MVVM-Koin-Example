package com.carlosgub.coroutines.di

import com.carlosgub.coroutines.features.books.data.datasource.rest.PostRestDataStore
import com.carlosgub.coroutines.features.books.data.repository.PostRepositoryImpl
import com.carlosgub.coroutines.features.books.domain.interactor.GetPostByIdInteractor
import com.carlosgub.coroutines.features.books.domain.interactor.GetPostsInteractor
import com.carlosgub.coroutines.features.books.domain.repository.PostRepository
import com.carlosgub.coroutines.features.books.presentation.viewmodel.PostViewModel
import com.carlosgub.coroutines.features.books.presentation.viewmodel.PostsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private val postModule = module {

    //region ViewModel
    viewModel {
        PostsViewModel(get())
    }
    viewModel {
        PostViewModel(get())
    }
    //endregion

    //region Interactor
    single {
        GetPostsInteractor(
            get()
        )
    }
    single {
        GetPostByIdInteractor(
            get()
        )
    }
    //endregion

    //region Repository
    single<PostRepository> {
        PostRepositoryImpl(get())
    }
    //endregion

    //region Datastore
    single {
        PostRestDataStore()
    }
    //endregion
}

val modules = listOf(postModule)