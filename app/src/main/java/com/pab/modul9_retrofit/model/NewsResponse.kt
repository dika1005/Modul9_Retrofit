package com.pab.modul9_retrofit.model

import com.google.gson.annotations.SerializedName

data class NewsResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("message")
    val message: String?,
    @SerializedName("data")
    val data: NewsData
)

data class NewsData(
    @SerializedName("link")
    val link: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("posts")
    val posts: List<NewsPost>
)

data class NewsPost(
    @SerializedName("link")
    val link: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("pubDate")
    val pubDate: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("thumbnail")
    val thumbnail: String
)
