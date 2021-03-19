package com.me.recyclerviewpreview.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.me.recyclerviewpreview.story.StoryClass
import com.me.recyclerviewpreview.databinding.ItemFeaturedStoryBinding
import com.me.recyclerviewpreview.databinding.ItemThumbnailStoryBinding
import com.me.recyclerviewpreview.util.load
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class StoryAdapter(private val itemClick: OnStoryClick) : ListAdapter<StoryClass, RecyclerView.ViewHolder>(StoryItemDiffCallBack()) {
    companion object {
        const val TYPE_FEATURED = 1
        const val TYPE_THUMBNAIL = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_FEATURED) {
            val bindingFeature = ItemFeaturedStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            FeaturedStoryHolder(bindingFeature, itemClick)
        } else {
            val bindingThumbnail = ItemThumbnailStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ThumbnailStoryHolder(bindingThumbnail, itemClick)
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
            holder.itemView.setOnClickListener { itemClick.onStoryClick(item, holder.layoutPosition) }
        } else {
            (holder as ThumbnailStoryHolder)
            holder.bind(item)
            holder.itemView.setOnClickListener { itemClick.onStoryClick(item, holder.layoutPosition) }
        }
    }

    private fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
        return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
    }

    class FeaturedStoryHolder(
            private val binding: ItemFeaturedStoryBinding,
            private val itemClick: OnStoryClick
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: StoryClass) {
            with(binding) {
                title.text = item.storyTitle
                description.text = item.storyDescription
                imageView.load(item.storyImageURL)
                if (!item.storyAuthor.isNullOrEmpty()) {
                    val stringTemp = "By ${item.storyAuthor} "
                    author.text = stringTemp
                } else {
                    author.text = ""
                }

                if (!item.storyDate.isNullOrEmpty()) {
                    time.text = getTimeAgo(item.storyDate)
                } else {
                    time.text = ""
                }
                save.setOnClickListener { itemClick.onSaveClick(item, layoutPosition) }
                share.setOnClickListener { itemClick.onShareClick(item, layoutPosition) }
            }
        }
    }

    class ThumbnailStoryHolder(
            private val binding: ItemThumbnailStoryBinding,
            private val itemClick: OnStoryClick
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(story: StoryClass) {
            binding.save.setOnClickListener {}
            with(binding) {

                titleThumbnail.text = story.storyTitle
                imageThumbnail.load(story.storyImageURL)
                if (!story.storyAuthor.isNullOrEmpty()) {
                    authorThumbnail.text = "By ${story.storyAuthor}"
                } else {
                    authorThumbnail.text = story.storyAuthor
                }

                if (!story.storyDate.isNullOrEmpty()) timeThumbnail.text = getTimeAgo(story.storyDate) else {
                    timeThumbnail.text = ""
                }
                share.setOnClickListener { itemClick.onShareClick(story, layoutPosition) }
                save.setOnClickListener { itemClick.onSaveClick(story, layoutPosition) }
            }
        }
    }

}

interface OnStoryClick {
    fun onStoryClick(story: StoryClass, position: Int)
    fun onSaveClick(story: StoryClass, position: Int)
    fun onShareClick(story: StoryClass, position: Int)
}

class StoryItemDiffCallBack : DiffUtil.ItemCallback<StoryClass>() {

    override fun areItemsTheSame(oldItem: StoryClass, newItem: StoryClass): Boolean = oldItem.storyID == newItem.storyID

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: StoryClass, newItem: StoryClass): Boolean = oldItem == newItem

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
