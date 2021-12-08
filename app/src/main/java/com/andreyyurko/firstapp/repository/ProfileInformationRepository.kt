package com.andreyyurko.firstapp.repository

import com.andreyyurko.firstapp.data.persistent.ProfileInformationStorage
import com.andreyyurko.firstapp.entity.ProfileInformation
import com.blelocking.di.AppCoroutineScope
import com.blelocking.di.IoCoroutineDispatcher
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ProfileInformationRepository @Inject constructor(
    private val profileInformationStorage: ProfileInformationStorage,
    @AppCoroutineScope externalCoroutineScope: CoroutineScope,
    @IoCoroutineDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun saveProfileInformation(profileInformation: ProfileInformation?) {
        withContext(ioDispatcher) {
            Timber.d("Save profile information $profileInformation")
            profileInformationStorage.profileInformation = profileInformation
        }
    }

    suspend fun getProfileInformationFlow() : StateFlow<ProfileInformation?> {
        return profileInformationFlow.await().asStateFlow()
    }

    private val profileInformationFlow: Deferred<MutableStateFlow<ProfileInformation?>> =
        externalCoroutineScope.async(context = ioDispatcher, start = CoroutineStart.LAZY) {
            Timber.d("Initializing auth tokens flow.")
            MutableStateFlow(
                profileInformationStorage.profileInformation
            )
        }
}