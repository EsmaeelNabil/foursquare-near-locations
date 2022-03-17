package com.esmaeel.challenge.utils.ktx

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.esmaeel.challenge.data.remote.PlacesService
import java.text.SimpleDateFormat
import java.util.*

fun Fragment.showToast(message: String?) = message?.let {
    makeToast(requireContext(), message)
}

fun makeToast(context: Context, message: String?) =
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

fun Activity.showToast(message: String?) = message?.let {
    makeToast(this, message)
}

fun getFormattedApiDate(): String =
    SimpleDateFormat(PlacesService.DATE_PATTERN, Locale.ROOT).format(Date())

val permissionList = arrayOf(
    Manifest.permission.ACCESS_FINE_LOCATION,
    Manifest.permission.ACCESS_COARSE_LOCATION
)

fun Activity.shouldShowRational() =
    this.shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION) ||
            this.shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)


fun Context.openSettings() {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
    intent.data = Uri.fromParts("package", this.packageName, null)
    this.startActivity(intent)
}

fun Context.hasLocationPermission() = ContextCompat.checkSelfPermission(
    this,
    Manifest.permission.ACCESS_FINE_LOCATION
) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
    this,
    Manifest.permission.ACCESS_COARSE_LOCATION
) == PackageManager.PERMISSION_GRANTED