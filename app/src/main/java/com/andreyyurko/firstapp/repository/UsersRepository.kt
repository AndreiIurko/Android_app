package com.andreyyurko.firstapp.repository

import com.andreyyurko.firstapp.data.network.Api
import com.andreyyurko.firstapp.data.network.GetUsersResponse
import com.andreyyurko.firstapp.data.network.response.error.GetUsersErrorResponce
import com.andreyyurko.firstapp.data.persistent.LocalKeyValueStorage
import com.andreyyurko.firstapp.entity.User
import com.blelocking.di.AppCoroutineScope
import com.blelocking.di.IoCoroutineDispatcher
import com.haroldadmin.cnradapter.NetworkResponse
import dagger.Lazy
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsersRepository @Inject constructor(
    private val apiLazy: Lazy<Api>,
    @AppCoroutineScope externalCoroutineScope: CoroutineScope,
    @IoCoroutineDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    private val api by lazy { apiLazy.get() }

    suspend fun getUsers() : NetworkResponse<List<User>, GetUsersErrorResponce> {
        return api.getUsers()
    }
}