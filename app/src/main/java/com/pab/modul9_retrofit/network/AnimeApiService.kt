package com.pab.modul9_retrofit.network

import com.pab.modul9_retrofit.model.AnimeInfoResponse
import com.pab.modul9_retrofit.model.AnimeScheduleItem
import com.pab.modul9_retrofit.model.AnimeSearchItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AnimeApiService {
    @GET("api/otakudesu/jadwal")
    fun getAnimeSchedule(): Call<List<AnimeScheduleItem>>

    @GET("api/otakudesu/anime")
    fun searchAnime(@Query("search") query: String): Call<List<AnimeSearchItem>>

    @GET("api/otakudesu/anime-info")
    fun getAnimeInfo(@Query("slug") slug: String): Call<AnimeInfoResponse>
}
