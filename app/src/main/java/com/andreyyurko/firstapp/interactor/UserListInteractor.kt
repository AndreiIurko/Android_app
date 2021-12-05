package com.andreyyurko.firstapp.interactor

import com.andreyyurko.firstapp.data.network.response.error.GetUsersErrorResponce
import com.andreyyurko.firstapp.entity.User
import com.andreyyurko.firstapp.repository.UsersRepository
import com.haroldadmin.cnradapter.NetworkResponse
import javax.inject.Inject

class UserListInteractor @Inject constructor(
    private val usersRepository: UsersRepository
) {
    suspend fun getUsers(): NetworkResponse<List<User>, GetUsersErrorResponce> {
        val response = usersRepository.getUsers()
        return response
    }
}