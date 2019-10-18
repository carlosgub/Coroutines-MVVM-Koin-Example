package com.carlosgub.coroutines.core.platform

import android.content.Context
import androidx.lifecycle.ViewModel
import com.carlosgub.coroutines.core.scheduler.Schedulers
import io.reactivex.disposables.CompositeDisposable
import org.koin.core.KoinComponent
import org.koin.core.inject

abstract class BaseViewModel : ViewModel(), KoinComponent {
    /*protected val context: Context by inject()
    protected val schedulerProvider: Schedulers by inject()
    protected val disposables = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }*/
}