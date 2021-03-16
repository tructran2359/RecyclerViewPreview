package com.me.recyclerviewpreview

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.me.recyclerviewpreview.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerView = binding.recyclerView
        val adapter = StoryAdapter()
        adapter.submitList(getData())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        findViewById<ImageView>(R.id.save).setOnClickListener { onClick(it) }
    }

    private fun onClick(view: View?) {
        if (view != null) {
            Toast.makeText(view.context, "test $", Toast.LENGTH_SHORT).show()
        }
        println("$view Clicked")
    }
}

