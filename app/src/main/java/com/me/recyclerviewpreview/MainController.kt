package com.me.recyclerviewpreview

class MainController(private val view: StoriesViewInterface, private val call: DataCallController) {
    fun getStoriesFromDataSource(){
        call.getStories(view)
    }
}