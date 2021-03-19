package com.me.recyclerviewpreview.Util

import androidx.recyclerview.widget.DiffUtil
import com.me.recyclerviewpreview.Story.StoryClass

class StoryUtil(private val oldList: List<StoryClass>, private val newList: List<StoryClass>) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

}