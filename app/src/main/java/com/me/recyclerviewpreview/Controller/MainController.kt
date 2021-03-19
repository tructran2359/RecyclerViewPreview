package com.me.recyclerviewpreview.Controller

import com.me.recyclerviewpreview.ViewInterface.StoriesViewInterface

class MainController(private val view: StoriesViewInterface, private val call: DataCallController) {
    fun getStoriesFromDataSource() {
        call.getStories(view)
    }
}