package com.imagine.android_imagine_tools.tools.ext

fun String.getDoubleLatitude(default:Double =0.0):Double{
    val lat = this.split(",").getOrNull(0)

    if(lat.isNullOrEmpty()) {
        return default
    }

    return lat.toDoubleOrNull()?:0.0
}

fun String.getDoubleLongitude(default:Double = 0.0):Double{
    val lng = this.split(",").getOrNull(1)

    if(lng.isNullOrEmpty()) {
        return default
    }

    return lng.toDoubleOrNull()?:0.0
}