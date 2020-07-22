package me.loidsemus.changelog.data

import java.util.*

data class Changelog(
    val id: Int?,
    var title: String,
    var description: String,
    val date: Date,
    val author: UUID,
    val features: MutableList<Feature> = mutableListOf()
) {

    fun addFeature(feature: Feature) {
        features.add(feature)
    }

    data class Feature(val id: Int?, var description: String)

}