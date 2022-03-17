package com.esmaeel.challenge.utils

import android.content.Context
import com.esmaeel.challenge.utils.ktx.hasLocationPermission
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PermissionsUtil @Inject constructor(@ApplicationContext val context: Context) {
    fun hasLocationsPermissions() = context.hasLocationPermission()
}