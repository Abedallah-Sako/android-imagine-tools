package com.imagine.jordanpass.tools.model


import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Ticket(
    @SerializedName("Date")
    val date: String?,
    @SerializedName("Email")
    val email: String?,
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
    @SerializedName("Residency")
    val residency: String?,
    @SerializedName("Type")
    val type: String?,
    @SerializedName("Baptism")
    val baptism: Boolean?,
    @SerializedName("QRImage")
    val qRImage: String?
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()?:"",
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(date)
        parcel.writeString(email)
        parcel.writeString(mobile)
        parcel.writeString(name)
        parcel.writeString(nationality)
        parcel.writeString(passNu)
        parcel.writeString(passportNu)
        parcel.writeString(price)
        parcel.writeString(residency)
        parcel.writeString(type)
        parcel.writeValue(baptism)
        parcel.writeString(qRImage)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Ticket> {
        override fun createFromParcel(parcel: Parcel): Ticket {
            return Ticket(parcel)
        }

        override fun newArray(size: Int): Array<Ticket?> {
            return arrayOfNulls(size)
        }
    }
}