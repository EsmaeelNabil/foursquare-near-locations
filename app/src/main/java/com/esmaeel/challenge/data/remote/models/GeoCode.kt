package com.esmaeel.challenge.data.remote.models

data class GeoCode(
    val main: Main
)

data class Main(
    val latitude: Double,
    val longitude: Double,
)