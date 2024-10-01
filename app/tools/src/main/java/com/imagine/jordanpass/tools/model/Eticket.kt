package com.imagine.jordanpass.tools.model


import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Eticket(
    @SerializedName("Date")
    val date: String?,
    @SerializedName("Email")
    val email: String?,
    @SerializedName("Location")
    val location: String?,
    @SerializedName("Mobile")
    val mobile: String?,
    @SerializedName("Name")
    val name: String?,
    @SerializedName("Nationality")
    val nationality: String?,
    @PrimaryKey
    @SerializedName("PassNu")
    val passNu: String,
    @SerializedName("PassportNu")
    val passportNu: String?,
    @SerializedName("Price")
    val price: String?,
    @SerializedName("QRImage")
    val qRImage: String?
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()?:"",
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(date)
        parcel.writeString(email)
        parcel.writeString(location)
        parcel.writeString(mobile)
        parcel.writeString(name)
        parcel.writeString(nationality)
        parcel.writeString(passNu)
        parcel.writeString(passportNu)
        parcel.writeString(price)
        parcel.writeString(qRImage)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Eticket> {
        override fun createFromParcel(parcel: Parcel): Eticket {
            return Eticket(parcel)
        }

        override fun newArray(size: Int): Array<Eticket?> {
            return arrayOfNulls(size)
        }
    }

}