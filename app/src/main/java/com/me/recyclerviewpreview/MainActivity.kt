package com.me.recyclerviewpreview

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.me.recyclerviewpreview.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(),StoriesViewInterface {
    private lateinit var listOfStories: List<StoryClass>
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: StoryAdapter
    private lateinit var controller : MainController
    private lateinit var recyclerView : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        recyclerView = binding.recyclerView

        controller = MainController(this,DataCallController())
        controller.getStoriesFromDataSource()

    }

    private val itemClick = object : OnStoryClick {
        override fun onStoryClick(story: StoryClass, position: Int) {
            Toast.makeText(
                baseContext,
                "Selected at : ${story.storyTitle} --- at position $position",
                Toast.LENGTH_SHORT
            ).show()
        }

        override fun onSaveClick(story: StoryClass, position: Int) {
            Toast.makeText(
                baseContext,
                "Selected Save at : ${story.storyTitle}  --- at position $position",
                Toast.LENGTH_SHORT
            ).show()
        }

        override fun onShareClick(story: StoryClass, position: Int) {
            Toast.makeText(
                baseContext,
                "Selected Share at : ${story.storyTitle}  --- at position $position",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun setUpAdapterAndView(listOfStories: List<StoryClass>) {
        this.listOfStories = listOfStories
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = StoryAdapter(itemClick)
        adapter.submitList(listOfStories)
        recyclerView.adapter = adapter

    }
}

