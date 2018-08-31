package com.example.fauzy.filmkuy.model

import com.google.gson.annotations.SerializedName

data class MovieVideosResponse(@SerializedName("results") val videos: ArrayList<MovieVideos>)