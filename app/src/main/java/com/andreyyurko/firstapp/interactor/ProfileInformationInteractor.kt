package com.andreyyurko.firstapp.interactor

import com.andreyyurko.firstapp.entity.ProfileInformation
import com.andreyyurko.firstapp.repository.ProfileInformationRepository
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class ProfileInformationInteractor @Inject constructor(
    private val profileInformationRepository: ProfileInformationRepository
) {
    suspend fun saveProfileInformation(profileInformation : ProfileInformation) {
        profileInformationRepository.saveProfileInformation(profileInformation)
    }

    suspend fun getProfileInformation(): StateFlow<ProfileInformation?> {
        return profileInformationRepository.getProfileInformationFlow()
    }

    suspend fun logout() {
        profileInformationRepository.saveProfileInformation(null)
    }
}