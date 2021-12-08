package com.andreyyurko.firstapp.data.di.module

import android.content.Context
import android.content.SharedPreferences
import com.andreyyurko.firstapp.data.persistent.LocalKeyValueStorage
import com.andreyyurko.firstapp.data.persistent.ProfileInformationStorage
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PersistencyModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(
            "our_awesome_app_local_key_value_storage",
            Context.MODE_PRIVATE
        )

    @Provides
    @Singleton
    fun provideLocalKeyValueStorage(pref: SharedPreferences, moshi: Moshi): LocalKeyValueStorage =
        LocalKeyValueStorage(pref, moshi)

    @Provides
    @Singleton
    fun provideProfileInformationStorage(pref: SharedPreferences, moshi: Moshi): ProfileInformationStorage =
        ProfileInformationStorage(pref, moshi)
}