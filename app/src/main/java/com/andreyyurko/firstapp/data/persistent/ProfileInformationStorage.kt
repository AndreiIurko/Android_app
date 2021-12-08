package com.andreyyurko.firstapp.data.persistent

import android.content.SharedPreferences
import androidx.core.content.edit
import com.andreyyurko.firstapp.entity.ProfileInformation
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.Moshi
import timber.log.Timber
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class ProfileInformationStorage(
    private val pref: SharedPreferences,
    moshi: Moshi
) {
    var profileInformation: ProfileInformation? by JsonDelegate(
        PROFILE_INFORMATION,
        pref,
        moshi.adapter(ProfileInformation::class.java)
    )


    private class JsonDelegate<T>(
        private val key: String,
        private val pref: SharedPreferences,
        private val adapter: JsonAdapter<T>
    ) : ReadWriteProperty<ProfileInformationStorage, T?> {

        override fun setValue(thisRef: ProfileInformationStorage, property: KProperty<*>, value: T?) {
            pref.edit(commit = true) {
                if (value == null) remove(key)
                else putString(key, adapter.toJson(value))
            }
        }

        override fun getValue(thisRef: ProfileInformationStorage, property: KProperty<*>): T? {
            return try {
                pref.getString(key, null)?.let { adapter.fromJson(it) }
            } catch (e: JsonDataException) {
                Timber.e(e)
                setValue(thisRef, property, null)
                null
            }
        }
    }

    companion object {
        private const val PROFILE_INFORMATION = "profile_information"
    }
}