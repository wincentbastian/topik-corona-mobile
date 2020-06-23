package com.example.topik_corona.model


import com.google.gson.annotations.SerializedName

data class News(
    @SerializedName("data")
    val `data`: List<Data>
)