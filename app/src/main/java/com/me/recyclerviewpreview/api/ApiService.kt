package com.me.recyclerviewpreview.api

import com.me.recyclerviewpreview.story.StoryClass
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("stories")
    fun getStory(): Call<List<StoryClass>>

}