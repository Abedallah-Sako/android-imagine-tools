package com.imagine.jordanpass.tools.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AttractionCityFilterModel(val cityName: String , var isChecked:Boolean = false):Parcelable