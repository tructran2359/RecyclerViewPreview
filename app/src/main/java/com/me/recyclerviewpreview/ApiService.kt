package com.me.recyclerviewpreview

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("stories")
    fun getStory(): Call<List<StoryClass>>

}