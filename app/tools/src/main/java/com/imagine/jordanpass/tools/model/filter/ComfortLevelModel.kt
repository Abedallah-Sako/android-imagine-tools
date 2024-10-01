package com.imagine.jordanpass.tools.model.filter

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ComfortLevelModel(val id:Int?,val title:String?,var isSelected:Boolean = false):Parcelable