package com.esmaeel.challenge.ui

import android.annotation.SuppressLint
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import com.esmaeel.challenge.R
import com.esmaeel.challenge.databinding.LocationActivityBinding
import com.esmaeel.challenge.ui.recommendationsList.RecommendationsListActivity
import com.esmaeel.challenge.utils.ktx.gone
import com.esmaeel.challenge.utils.ktx.hasLocationPermission
import com.esmaeel.challenge.utils.ktx.showToast
import com.skydoves.bundler.intentOf
import dagger.hilt.android.AndroidEntryPoint
import fr.quentinklein.slt.LocationTracker
import fr.quentinklein.slt.ProviderError
import java.util.*
import kotlin.properties.Delegates


@AndroidEntryPoint
class LocationActivity : AppCompatActivity() {

    private val binder by lazy { LocationActivityBinding.inflate(layoutInflater) }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        lastKnowLocation?.let {
            outState.putParcelable("location", it)
        }
    }

    // track location updates every 200 millis OR 10km
    private val locationTracker = LocationTracker(
        minTimeBetweenUpdates = 200.toLong(),
        minDistanceBetweenUpdates = 10f,
        shouldUseGPS = true,
        shouldUseNetwork = true,
        shouldUsePassive = true
    )

    var lastKnowLocation: Location? by Delegates.observable(null) { property, oldValue, newValue ->
        if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
            newValue?.let {
                val geocoder = Geocoder(this, Locale.getDefault())
                val addresses: List<Address> =
                    geocoder.getFromLocation(it.latitude, it.longitude, 1)
                binder.locationTxt.text = """
                    ${addresses.firstOrNull()?.getAddressLine(0) ?: ""}
                    
                    ${it.latitude},${it.longitude}
                    
                    Press Next to show list of Venuses
                """.trimIndent()

                binder.next.show()
            } ?: run {
                binder.locationTxt.text = getString(R.string.waiting_for_location)
                binder.next.gone()
            }
        }
    }

    private val locationListener = object : LocationTracker.Listener {
        override fun onLocationFound(location: Location) {
            lastKnowLocation = location
        }

        override fun onProviderError(providerError: ProviderError) {
            providerError.message?.let(::showToast)
        }
    }


    @SuppressLint("MissingPermission")
    override fun onStart() {
        super.onStart()
        if (hasLocationPermission())
            locationTracker.startListening(this)
        else openConfigurationActivity()
    }

    private fun openConfigurationActivity() {
        intentOf<ConfigurationActivity> {
            startActivity(this@LocationActivity)
            finishAffinity()
        }
    }

    override fun onStop() {
        super.onStop()
        locationTracker.stopListening()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binder.root)
        savedInstanceState?.let {
            lastKnowLocation = it.getParcelable("location")
        }
        locationTracker.addListener(locationListener)
        binder.next.setOnClickListener { goNext() }

    }

    private fun goNext() {
        lastKnowLocation?.let {
            intentOf<RecommendationsListActivity> {
                putExtra(RecommendationsListActivity.LAT to it.latitude)
                putExtra(RecommendationsListActivity.LONG to it.longitude)
                startActivity(this@LocationActivity)
            }
        }
    }
}