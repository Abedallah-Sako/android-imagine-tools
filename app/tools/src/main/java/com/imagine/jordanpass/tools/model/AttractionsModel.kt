package com.imagine.jordanpass.tools.model


import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class AttractionsModel(): ArrayList<AttractionsModel.AttractionsModelItem>(){

    data class AttractionsModelItem(
        @SerializedName("description")
        val description: String,
        @SerializedName("id")
        val id: Int,
        @SerializedName("link")
        val link: Any?,
        @SerializedName("mainimage")
        val mainimage: Any?,
        @SerializedName("name")
        val name: String,
        @SerializedName("storydate")
        val storydate: Any?,
        @SerializedName("storyimage")
        val storyimage: String,
        var isFavorites: Boolean = false
    ): Parcelable {
        constructor(parcel: Parcel) : this(
            parcel.readString()?:"",
            parcel.readInt(),
            parcel.readString()?:"",
            parcel.readString()?:"",
            parcel.readString()?:"",
            parcel.readString()?:"",
            parcel.readString()?:"",
            parcel.readByte() != 0.toByte()
        ) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(description)
            parcel.writeInt(id)
            parcel.writeString(name)
            parcel.writeString(storyimage)
            parcel.writeByte(if (isFavorites) 1 else 0)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<AttractionsModelItem> {
            override fun createFromParcel(parcel: Parcel): AttractionsModelItem {
                return AttractionsModelItem(parcel)
            }

            override fun newArray(size: Int): Array<AttractionsModelItem?> {
                return arrayOfNulls(size)
            }
        }
    }
}