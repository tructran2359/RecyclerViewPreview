package com.me.recyclerviewpreview

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.me.recyclerviewpreview.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(){
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerView = binding.recyclerView
        val adapter = StoryAdapter(a)
        adapter.submitList(getData())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private val a = object : OnStoryClick {

        override fun onStoryClick(story: Story, position: Int) {
            Toast.makeText(baseContext, "Selected at : ${story.title} --- at position $position", Toast.LENGTH_SHORT).show()
        }

        override fun onSaveClick(story: Story, position: Int) {
            Toast.makeText(baseContext, "Selected Save at : ${story.title}  --- at position $position", Toast.LENGTH_SHORT).show()
        }

        override fun onShareClick(story: Story, position: Int) {
            Toast.makeText(baseContext, "Selected Share at : ${story.title}  --- at position $position", Toast.LENGTH_SHORT).show()
        }

    }
}

