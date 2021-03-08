package com.me.recyclerviewpreview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
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

    class FeaturedStoryHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var titleTextView: TextView = itemView.findViewById(R.id.title)
        var descriptionTextView: TextView = itemView.findViewById(R.id.description)
        var imageView: ImageView = itemView.findViewById(R.id.imageView)
        var authorTextView: TextView = itemView.findViewById(R.id.author)
        var timeTextView: TextView = itemView.findViewById(R.id.time)

        fun bind(item: Story) {
            titleTextView.text = item.title
            descriptionTextView.text = item.description
            imageView.load(item.imageUrl)
            if (item.author!=null){
                authorTextView.text = "By " + item.author
            }
            else {
                authorTextView.text = item.author
            }

            if (item.releaseDate!=null){
                timeTextView.text = getTimeAgo(item.releaseDate)
            }

        }
    }

    class ThumbnailStoryHolder(thumbnailView: View) : RecyclerView.ViewHolder(thumbnailView) {
        var titleThumbnail: TextView = itemView.findViewById(R.id.title_thumbnail)
        var imageThumbnail: ImageView = itemView.findViewById(R.id.image_thumbnail)
        var authorThumbnail: TextView = itemView.findViewById(R.id.author_thumbnail)
        var timeThumbnail: TextView = itemView.findViewById(R.id.time_thumbnail)

        fun bind(story: Story) {
            titleThumbnail.text = story.title
            imageThumbnail.load(story.imageUrl)
            if (story.author!=null){
                authorThumbnail.text = "By " + story.author
            }
            else {
                authorThumbnail.text = story.author
            }

            if (story.releaseDate!=null){
                timeThumbnail.text = getTimeAgo(story.releaseDate)
            }

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_FEATURED) {
            val view = parent.inflate(R.layout.item_featured_story)
            FeaturedStoryHolder(view)
        } else{
            val view = parent.inflate(R.layout.item_thumbnail_story)
            ThumbnailStoryHolder(view)
        }

    }

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            TYPE_FEATURED
        } else {
            TYPE_THUMBNAIL
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
//        when (holder.itemViewType){
//            0 -> (holder as FeaturedStoryHolder).run { bind(item) }
//            else -> (holder  as ThumbnailStoryHolder).run{ bind(item)}
//        }
        if(holder.itemViewType==1){
            (holder as FeaturedStoryHolder).run { bind(item) }
        }else {
            (holder  as ThumbnailStoryHolder).run{ bind(item)}
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
fun getTimeAgo(timeString:String): String{
    val dateFormat = SimpleDateFormat("yyyy-mm-dd'T'HH:mm:ss")
    val releaseTimeFormatted = dateFormat.parse(timeString)
    val nowTime = Date()
    val seconds: Long = TimeUnit.MILLISECONDS.toSeconds(nowTime.time-releaseTimeFormatted.time)
    val minutes: Long = TimeUnit.MILLISECONDS.toMinutes(nowTime.time-releaseTimeFormatted.time)
    val hours: Long = TimeUnit.MILLISECONDS.toHours(nowTime.time-releaseTimeFormatted.time)
    val days: Long = TimeUnit.MILLISECONDS.toDays(nowTime.time-releaseTimeFormatted.time)
    return when {
        seconds<60 -> {
            ("$seconds seconds ago")
        }
        minutes<60 -> {
            ("$minutes minutes ago")
        }
        hours<24 -> {
            ("$hours hours ago")
        }
        else -> {
            ("$days days ago")
        }
    }
}