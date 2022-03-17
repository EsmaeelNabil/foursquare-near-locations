package com.esmaeel.challenge.data.remote.models

data class RecommendationResult(
    val categories: List<Category>,
    val distance: Int,
    val geocode: GeoCode,
    val location: Location,
    val name: String,
    val timezone: String,
) {
    fun getImage(): String {
        val icon = categories.getOrNull(0)?.icon
            ?: return "https://cdn-icons-png.flaticon.com/512/48/48703.png"
        return "${icon.prefix}64${icon.suffix}"
    }
}