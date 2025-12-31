package com.pab.modul9_retrofit.model

import com.google.gson.annotations.SerializedName

// Response untuk /api/otakudesu/jadwal
data class AnimeScheduleItem(
    @SerializedName("hari")
    val hari: String,
    @SerializedName("anime")
    val anime: List<Anime>
)

data class Anime(
    @SerializedName("judul")
    val judul: String,
    @SerializedName("slug")
    val slug: String
)

// Response untuk /api/otakudesu/anime?search=...
data class AnimeSearchItem(
    @SerializedName("gambar")
    val gambar: String?,
    @SerializedName("judul")
    val judul: String,
    @SerializedName("slug")
    val slug: String,
    @SerializedName("eps")
    val eps: List<String>?,
    @SerializedName("rate")
    val rate: List<String>?
)

// Response untuk /api/otakudesu/anime-info?slug=...
data class AnimeInfoResponse(
    @SerializedName("judul")
    val judul: String?,
    @SerializedName("thumbnail")
    val thumbnail: String?,
    @SerializedName("sinopsis")
    val sinopsis: String?,
    @SerializedName("genre")
    val genre: List<String>?,
    @SerializedName("episode_list")
    val episodeList: List<EpisodeItem>?
)

data class EpisodeItem(
    @SerializedName("judul")
    val judul: String,
    @SerializedName("slug")
    val slug: String,
    @SerializedName("tanggal")
    val tanggal: String?
)
