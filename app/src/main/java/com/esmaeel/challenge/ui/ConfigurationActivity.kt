package com.esmaeel.challenge.ui

import android.Manifest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.esmaeel.challenge.R
import com.esmaeel.challenge.databinding.ConfigurationActivityBinding
import com.esmaeel.challenge.utils.ktx.*
import com.innfinity.permissionflow.lib.requestPermissions
import com.skydoves.bundler.intentOf
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ConfigurationActivity : AppCompatActivity() {

    private val binder by lazy { ConfigurationActivityBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binder.root)
        binder.next.setOnClickListener { goNext() }
        binder.openSettings.setOnClickListener { openSettings() }
        binder.image.setOnClickListener { checkPermissions() }
        binder.title.setOnClickListener { checkPermissions() }
        checkPermissions()
    }

    /**
     * we could use registerForActivity new apis for requesting permissions
     * but for simplicity, i have chosen a library that does the same rather-
     * than reinventing the wheel.
     */
    private fun checkPermissions() {
        lifecycleScope.launch(Dispatchers.Main) {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                // just call requestPermission and pass in all required permissions
                when {
                    hasLocationPermission() -> goNext()
                    shouldShowRational() -> {
                        binder.title.text = getString(R.string.permissions_needed)
                        binder.next.gone()
                        binder.openSettings.show()
                    }
                    else -> {
                        binder.title.text = getString(R.string.permissions_needed)
                        binder.next.gone()

                        requestPermissions(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        ).collect { permissions ->
                            // here you get the result of the requests, permissions holds a list of Permission requests and you can check if all of them have been granted:
                            val allGranted = permissions.find { !it.isGranted } == null
                            if (allGranted) {
                                binder.title.text = getString(R.string.permissions_granted)
                                binder.next.show()
                                binder.openSettings.gone()
                            }
                        }
                    }
                }
            }
        }

    }

    private fun goNext() {
        intentOf<LocationActivity> {
            startActivity(this@ConfigurationActivity)
            finishAffinity()
        }
    }
}