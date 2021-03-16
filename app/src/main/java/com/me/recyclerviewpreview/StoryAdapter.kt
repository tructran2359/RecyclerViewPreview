package com.me.recyclerviewpreview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.me.recyclerviewpreview.databinding.ItemFeaturedStoryBinding
import com.me.recyclerviewpreview.databinding.ItemThumbnailStoryBinding
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class StoryItemDiffCallBack : DiffUtil.ItemCallback<Story>() {
    override fun areItemsTheSame(oldItem: Story, newItem: Story): Boolean = oldItem == newItem

    override fun areContentsTheSame(oldItem: Story, newItem: Story): Boolean = oldItem == newItem

}
class FeaturedStoryHolder(
        private val binding: ItemFeaturedStoryBinding
) : RecyclerView.ViewHolder(binding.root)
        , View.OnClickListener {

    fun bind(item: Story) {
        with(binding) {
            title.text = item.title
            description.text = item.description
            imageView.load(item.imageUrl)
            if (item.author != null && item.author.isNotEmpty()) {
                val stringTemp = "By " + item.author
                author.text = stringTemp
            } else {
                author.text = ""
            }

            if (item.releaseDate != null && item.releaseDate.isNotEmpty()) {
                time.text = getTimeAgo(item.releaseDate)
            } else {
                time.text = ""
            }
            save.setOnClickListener { onSaveClick(it) }
            share.setOnClickListener { onShareClick(it) }
        }
    }

    private fun onShareClick(view: View?) {
        if (view != null) {
            Toast.makeText(view.context, "Share Clicked at position $layoutPosition ", Toast.LENGTH_SHORT).show()
        }
        println("Share Clicked")
    }

    private fun onSaveClick(view: View?) {
        if (view != null) {
            Toast.makeText(view.context, "Save Clicked at position $layoutPosition ", Toast.LENGTH_SHORT).show()
        }
        println("Save Clicked")
    }

    override fun onClick(view: View?) {
        if (view != null) {
            val tempDisplay = getData()[layoutPosition].title
            Toast.makeText(view.context, "Feature story selected at article :$tempDisplay ", Toast.LENGTH_SHORT).show()
        }
        println("$view Clicked")
    }

}

class ThumbnailStoryHolder(
        private val binding: ItemThumbnailStoryBinding)
    : RecyclerView.ViewHolder(binding.root), View.OnClickListener {
    fun bind(story: Story) {
        with(binding) {

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
            share.setOnClickListener { onShareClick(it) }
            save.setOnClickListener { onSaveClick(it) }
        }
    }

    private fun onShareClick(view: View?) {
        if (view != null) {
            Toast.makeText(view.context, "Thumbnail Share Clicked at position $layoutPosition", Toast.LENGTH_SHORT).show()
        }
        println("Share Clicked")
    }

    private fun onSaveClick(view: View?) {
        if (view != null) {
            Toast.makeText(view.context, "Thumbnail Save Clicked at position $layoutPosition", Toast.LENGTH_SHORT).show()
        }
        println("Save Clicked")
    }

    override fun onClick(view: View?) {
        if (view != null) {
            val tempDisplay = getData()[layoutPosition].title
            Toast.makeText(view.context, "Thumbnail Story Clicked at article : $tempDisplay", Toast.LENGTH_SHORT).show()
        }
        println("$view Clicked")
    }
}

class StoryAdapter : ListAdapter<Story, RecyclerView.ViewHolder>(StoryItemDiffCallBack()) {
    companion object {
        const val TYPE_FEATURED = 1
        const val TYPE_THUMBNAIL = 2
    }

    private val items = mutableListOf<Story>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_FEATURED) {
            val bindingFeature = ItemFeaturedStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            FeaturedStoryHolder(bindingFeature)
        } else {
            val bindingThumbnail = ItemThumbnailStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ThumbnailStoryHolder(bindingThumbnail)
        }
    }
    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            TYPE_FEATURED
        } else {
            TYPE_THUMBNAIL
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        if (holder.itemViewType == 1) {
            (holder as FeaturedStoryHolder)
            holder.bind(item)
            holder.itemView.setOnClickListener { holder.onClick(it) }
        } else {
            (holder as ThumbnailStoryHolder)
            holder.bind(item)
            holder.itemView.setOnClickListener { holder.onClick(it) }
        }
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
