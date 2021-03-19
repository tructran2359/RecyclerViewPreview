package com.me.recyclerviewpreview.story

import com.google.gson.annotations.SerializedName

class StoryClass {
    @SerializedName("nid")
    val storyID: Int? = null

    @SerializedName("title")
    val storyTitle: String? = null

    @SerializedName("author")
    val storyAuthor: String? = null

    @SerializedName("release_date")
    val storyDate: String? = null

    @SerializedName("description")
    val storyDescription: String? = null

    @SerializedName("image_url")
    val storyImageURL: String? = null
}
