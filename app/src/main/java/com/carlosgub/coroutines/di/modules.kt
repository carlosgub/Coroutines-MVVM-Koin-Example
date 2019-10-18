package com.carlosgub.coroutines.di

import com.carlosgub.coroutines.features.books.data.datasource.rest.PostRestDataStore
import com.carlosgub.coroutines.core.scheduler.Schedulers
import com.carlosgub.coroutines.core.scheduler.SchedulersImpl
import com.carlosgub.coroutines.features.books.data.repository.PostRepositoryImpl
import com.carlosgub.coroutines.features.books.domain.interactor.GetPostByIdInteractor
import com.carlosgub.coroutines.features.books.domain.interactor.GetPostsInteractor
import com.carlosgub.coroutines.features.books.domain.repository.PostRepository
import com.carlosgub.coroutines.features.books.presentation.viewmodel.PostDetailViewModel
import com.carlosgub.coroutines.features.books.presentation.viewmodel.PostViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private val appModule = module {
    single<Schedulers> {
        SchedulersImpl()
    }
}


private val postModule = module {

    //region ViewModel
    viewModel {
        PostViewModel(get())
    }
    viewModel {
        PostDetailViewModel(get())
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

val modules = listOf(
    appModule, postModule
)