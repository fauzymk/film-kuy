package com.example.fauzy.filmkuy.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class MovieCast (
        val cast_id: Int,
        val character: String,
        val id: Int,
        val name: String,
        @SerializedName("profile_path") val prof_pic: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(cast_id)
        parcel.writeString(character)
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(prof_pic)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MovieCast> {
        override fun createFromParcel(parcel: Parcel): MovieCast {
            return MovieCast(parcel)
        }

        override fun newArray(size: Int): Array<MovieCast?> {
            return arrayOfNulls(size)
        }
    }
}