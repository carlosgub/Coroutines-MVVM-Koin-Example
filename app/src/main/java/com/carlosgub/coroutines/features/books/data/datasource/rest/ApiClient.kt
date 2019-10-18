package com.carlosgub.coroutines.features.books.data.datasource.rest

import com.carlosgub.coroutines.core.network.BaseApiClient
import com.carlosgub.coroutines.features.books.data.datasource.rest.interfaces.IPostApiClient

object PostApiClient : BaseApiClient<IPostApiClient>(IPostApiClient::class.java)