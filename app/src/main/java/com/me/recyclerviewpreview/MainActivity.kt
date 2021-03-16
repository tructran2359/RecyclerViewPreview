package com.me.recyclerviewpreview

import android.os.Bundle
import android.view.View
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
    }

    fun onVHClick(view: View?, position: Int) {
        if (view != null) {
            val tempDisplay = getData()[position].title
            Toast.makeText(view.context, "Selected Article : $tempDisplay", Toast.LENGTH_SHORT).show()
        }
    }

    fun onSaveClick(view: View?, position: Int) {
        if (view != null) {
            Toast.makeText(view.context, "Save Clicked at position $position ", Toast.LENGTH_SHORT).show()
        }
    }

    fun onShareClick(view: View?, position: Int) {
        if (view != null) {
            Toast.makeText(view.context, "Share Clicked at position $position ", Toast.LENGTH_SHORT).show()
        }
    }
}

