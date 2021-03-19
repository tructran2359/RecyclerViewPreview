package com.me.recyclerviewpreview.Api

import com.me.recyclerviewpreview.Story.StoryClass
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("stories")
    fun getStory(): Call<List<StoryClass>>

}