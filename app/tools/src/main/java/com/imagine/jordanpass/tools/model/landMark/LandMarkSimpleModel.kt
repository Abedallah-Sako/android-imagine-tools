package com.imagine.jordanpass.tools.model.landMark

data class LandMarkSimpleModel(
    val title: String?,
    val desc: String?,
    val distanceMeters: String?,
    val distanceInKm: String?,
    val category: List<Int?>?,
    val comfortLevel: String?,
)