package com.me.recyclerviewpreview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.me.recyclerviewpreview.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bind = ActivityMainBinding.inflate(layoutInflater)

        val recyclerView = bind.recyclerview
        val adapter = StoryAdapter()
        adapter.setItems(getData())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        setContentView(bind.root)
    }
}