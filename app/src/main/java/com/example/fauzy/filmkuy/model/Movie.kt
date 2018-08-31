package com.example.fauzy.filmkuy.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Created by fauzy on 16/07/18.
 */
data class Movie (
        val id: Int,
        val title: String,
        @SerializedName("poster_path") val poster: String,
        @SerializedName("backdrop_path") val backdrop: String?,
        val overview: String,
        val vote_average: Double,
        val release_date: String,
        val credits: MovieCastResponse? = null,
        val videos: MovieVideosResponse? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readDouble(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(poster)
        parcel.writeString(backdrop)
        parcel.writeString(overview)
        parcel.writeDouble(vote_average)
        parcel.writeString(release_date)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Movie> {
        override fun createFromParcel(parcel: Parcel): Movie {
            return Movie(parcel)
        }

        override fun newArray(size: Int): Array<Movie?> {
            return arrayOfNulls(size)
        }
    }
}