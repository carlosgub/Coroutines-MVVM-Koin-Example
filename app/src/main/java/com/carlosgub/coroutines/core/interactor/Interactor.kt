package com.carlosgub.coroutines.core.interactor

interface Interactor<in Params, out Type> {

    suspend fun execute(params: Params): Type

    object None
}