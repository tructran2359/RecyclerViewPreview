package com.me.recyclerviewpreview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.me.recyclerviewpreview.databinding.ItemFeaturedStoryBinding
import com.me.recyclerviewpreview.databinding.ItemThumbnailStoryBinding
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class StoryAdapter : ListAdapter<Story, RecyclerView.ViewHolder>(StoryItemDiffCallBack()) {
    companion object {
        const val TYPE_FEATURED = 1
        const val TYPE_THUMBNAIL = 2
    }

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
            holder.itemView.setOnClickListener { MainActivity().onVHClick(it,position) }
        } else {
            (holder as ThumbnailStoryHolder)
            holder.bind(item)
            holder.itemView.setOnClickListener { MainActivity().onVHClick(it,position) }
        }
    }

    private fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
        return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
    }

    class FeaturedStoryHolder(
            private val binding: ItemFeaturedStoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Story) {
            with(binding) {
                title.text = item.title
                description.text = item.description
                imageView.load(item.imageUrl)
                if (!item.author.isNullOrEmpty()) {
                    val stringTemp = "By ${item.author} "
                    author.text = stringTemp
                } else {
                    author.text = ""
                }

                if (!item.releaseDate.isNullOrEmpty()) {
                    time.text = getTimeAgo(item.releaseDate)
                } else {
                    time.text = ""
                }
                save.setOnClickListener { MainActivity().onSaveClick(it,layoutPosition) }
                share.setOnClickListener { MainActivity().onShareClick(it,layoutPosition) }
            }
        }
    }

    class ThumbnailStoryHolder(
            private val binding: ItemThumbnailStoryBinding)
        : RecyclerView.ViewHolder(binding.root){
        fun bind(story: Story) {
            with(binding) {

                titleThumbnail.text = story.title
                imageThumbnail.load(story.imageUrl)
                if (!story.author.isNullOrEmpty()) {
                    authorThumbnail.text = "By ${story.author}"
                } else {
                    authorThumbnail.text = story.author
                }

                if (!story.releaseDate.isNullOrEmpty()) {
                    timeThumbnail.text = getTimeAgo(story.releaseDate)
                } else {
                    timeThumbnail.text = ""
                }
                share.setOnClickListener { MainActivity().onShareClick(it,layoutPosition) }
                save.setOnClickListener { MainActivity().onSaveClick(it,layoutPosition) }
            }
        }
    }

    interface OnStoryClick {
        fun onStoryClick(){
        }

        fun onSaveClick(){
        }

        fun onShareClick(){
        }

    }
}

class StoryItemDiffCallBack : DiffUtil.ItemCallback<Story>() {

    override fun areItemsTheSame(oldItem: Story, newItem: Story): Boolean = oldItem.nid == newItem.nid

    override fun areContentsTheSame(oldItem: Story, newItem: Story): Boolean = oldItem == newItem

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
