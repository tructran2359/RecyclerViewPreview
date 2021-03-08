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

    class ThumbnailStoryHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(story: Story) {}
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == TYPE_FEATURED) {


        }
        val view = parent.inflate(R.layout.item_featured_story, false)
        return FeaturedStoryHolder(view)
    }

    override fun getItemCount() = items.size

//    override fun getItemViewType(position: Int): Int {
////        return if (position == 0) {
////            TYPE_FEATURED
////        } else {
////            TYPE_THUMBNAIL
////        }
//    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        (holder as FeaturedStoryHolder).run {
            bind(item)
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