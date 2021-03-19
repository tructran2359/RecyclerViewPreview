package com.me.recyclerviewpreview.controller

import com.me.recyclerviewpreview.viewInterface.StoriesViewInterface

class MainController(private val view: StoriesViewInterface, private val call: DataCallController) {
    fun getStoriesFromDataSource() {
        call.getStories(view)
    }
}