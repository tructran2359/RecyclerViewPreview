package com.me.recyclerviewpreview

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.me.recyclerviewpreview.controller.DataCallController
import com.me.recyclerviewpreview.controller.MainController
import com.me.recyclerviewpreview.story.StoryClass
import com.me.recyclerviewpreview.viewInterface.StoriesViewInterface
import com.me.recyclerviewpreview.adapter.OnStoryClick
import com.me.recyclerviewpreview.adapter.StoryAdapter
import com.me.recyclerviewpreview.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), StoriesViewInterface {
    private lateinit var adapter: StoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = StoryAdapter(itemClick)
        recyclerView.adapter = adapter

        val controller = MainController(this, DataCallController())
        controller.getStoriesFromDataSource()

    }

    private val itemClick = object : OnStoryClick {
        override fun onStoryClick(story: StoryClass, position: Int) {
            Toast.makeText(
                    this@MainActivity,
                    "Selected at : ${story.storyTitle} --- at position $position",
                    Toast.LENGTH_SHORT
            ).show()
        }

        override fun onSaveClick(story: StoryClass, position: Int) {
            Toast.makeText(
                    this@MainActivity,
                    "Selected Save at : ${story.storyTitle}  --- at position $position",
                    Toast.LENGTH_SHORT
            ).show()
        }

        override fun onShareClick(story: StoryClass, position: Int) {
            Toast.makeText(
                    this@MainActivity,
                    "Selected Share at : ${story.storyTitle}  --- at position $position",
                    Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun setUpAdapterAndView(listOfStories: List<StoryClass>) {
        adapter.submitList(listOfStories)
    }
}

