package com.imagine.jordanpass.tools.model.story


import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class StoryLocations(
        @SerializedName("Id")
        val id: Int?, // 1
        @SerializedName("Name")
        val name: String?, // Irbid
        var isChecked:Boolean = false
    ):Parcelable {
        constructor(parcel: Parcel) : this(
                parcel.readValue(Int::class.java.classLoader) as? Int,
                parcel.readString(),
                parcel.readByte() != 0.toByte()
        ) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
                parcel.writeValue(id)
                parcel.writeString(name)
                parcel.writeByte(if (isChecked) 1 else 0)
        }

        override fun describeContents(): Int {
                return 0
        }

        companion object CREATOR : Parcelable.Creator<StoryLocations> {
                override fun createFromParcel(parcel: Parcel): StoryLocations {
                        return StoryLocations(parcel)
                }

                override fun newArray(size: Int): Array<StoryLocations?> {
                        return arrayOfNulls(size)
                }
        }
}