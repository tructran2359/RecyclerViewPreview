package com.me.recyclerviewpreview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.me.recyclerviewpreview.databinding.ItemFeaturedStoryBinding
import com.me.recyclerviewpreview.databinding.ItemThumbnailStoryBinding
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class StoryAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val TYPE_FEATURED = 1
        const val TYPE_THUMBNAIL = 2
    }

    private val items = mutableListOf<Story>()

    class FeaturedStoryHolder(private val binding:ItemFeaturedStoryBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Story) {
            with(binding) {
                title.text = item.title
                description.text = item.description
                imageView.load(item.imageUrl)
                if (item.author != null && item.author.isNotEmpty()) {
//                    println("By " + item.author)
                    val stringTemp = "By " + item.author
                    author.text = stringTemp
                    println(author.text)
                } else {
                    author.text = ""
                    println(item.author)
                }

                if (item.releaseDate != null && item.releaseDate.isNotEmpty()) {
                    time.text = getTimeAgo(item.releaseDate)
                } else {
                    time.text = ""
                }
            }

        }


    }

    class ThumbnailStoryHolder(private val binding:ItemThumbnailStoryBinding) : RecyclerView.ViewHolder(binding.root) {
//        private val binding = ItemThumbnailStoryBinding.bind(thumbnailView)

        fun bind(story: Story) {
            with(binding){

            titleThumbnail.text = story.title
            imageThumbnail.load(story.imageUrl)
            if (story.author != null && story.author.isNotEmpty()) {
                authorThumbnail.text = "By " + story.author
            } else {
                authorThumbnail.text = story.author
            }

            if (story.releaseDate != null && story.releaseDate.isNotEmpty()) {
                timeThumbnail.text = getTimeAgo(story.releaseDate)
            } else {
                timeThumbnail.text = ""
            }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_FEATURED) {
            val bindingFeature = ItemFeaturedStoryBinding.inflate(LayoutInflater.from(parent.context),parent,false)
//            val view = parent.inflate(R.layout.item_featured_story)
            FeaturedStoryHolder(bindingFeature)
        } else {
            val bindingThumbnail = ItemThumbnailStoryBinding.inflate(LayoutInflater.from(parent.context),parent,false)
//            val view = parent.inflate(R.layout.item_thumbnail_story)
            ThumbnailStoryHolder(bindingThumbnail)
        }
    }

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int): Int {
        return if (position == 0 ) {
            TYPE_FEATURED
        } else {
            TYPE_THUMBNAIL
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        if (holder.itemViewType == 1) {
            (holder as FeaturedStoryHolder)
            holder.bind(item)
        } else {
            (holder as ThumbnailStoryHolder)
            holder.bind(item)
        }

    }

    fun setItems(items: List<Story>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    private fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
        return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
    }

}

fun getTimeAgo(timeString: String): String {
    val dateFormat = SimpleDateFormat("yyyy-mm-dd'T'HH:mm:ss")
    val releaseTimeFormatted = dateFormat.parse(timeString)
    val nowTime = Date()
    val seconds: Long = TimeUnit.MILLISECONDS.toSeconds(nowTime.time - releaseTimeFormatted.time)
    val minutes: Long = TimeUnit.MILLISECONDS.toMinutes(nowTime.time - releaseTimeFormatted.time)
    val hours: Long = TimeUnit.MILLISECONDS.toHours(nowTime.time - releaseTimeFormatted.time)
    val days: Long = TimeUnit.MILLISECONDS.toDays(nowTime.time - releaseTimeFormatted.time)
    return when {
        seconds < 60 -> {
            ("$seconds seconds ago")
        }
        minutes < 60 -> {
            ("$minutes minutes ago")
        }
        hours < 24 -> {
            ("$hours hours ago")
        }
        else -> {
            ("$days days ago")
        }
    }
}
