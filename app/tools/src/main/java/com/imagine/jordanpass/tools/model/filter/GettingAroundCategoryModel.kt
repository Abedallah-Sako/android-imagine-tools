package com.imagine.jordanpass.tools.model.filter

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class GettingAroundCategoryModel(
    @SerializedName("Id")
    val id: Int?,
    @SerializedName("Name")
    val name: String?,
    var isSelected: Boolean = false
):Parcelable