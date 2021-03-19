package com.me.recyclerviewpreview

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DataCallController {
    private val manager = ApiManager()
    fun getStories(viewInterface: StoriesViewInterface){
        val call : Call<List<StoryClass>> = manager.getService()!!.getStory()
        call.enqueue(object : Callback<List<StoryClass>> {
            override fun onResponse(
                call: Call<List<StoryClass>>,
                response: Response<List<StoryClass>>
            ) {
                if (!response.isSuccessful) {
                    return
                } else {
                    val stories: List<StoryClass>? = response.body()
                    if (stories != null) {
                        viewInterface.setUpAdapterAndView(stories)
                    }

                }
            }

            override fun onFailure(call: Call<List<StoryClass>>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
}