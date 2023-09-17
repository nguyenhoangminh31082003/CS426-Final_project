package com.example.cs426_final_project.adapters

import android.R.drawable
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cs426_final_project.R
import com.example.cs426_final_project.models.FeedInfo
import com.example.cs426_final_project.utilities.ImageUtilityClass


class RecyclerFeedViewPagerAdapter(
    private val feedList : List<FeedInfo>,
    private val listener : OnItemClickListener,
) : RecyclerView.Adapter<RecyclerFeedViewPagerAdapter.RecyclerViewPagerViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    class RecyclerViewPagerViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        val txtUsernameFeed = itemView.findViewById<TextView>(R.id.txtUsernameFeed)!!
        val txtCommentFeed = itemView.findViewById<TextView>(R.id.txtCommentFeed)!!
        val txtDateFeed = itemView.findViewById<TextView>(R.id.txtDateFeed)!!
        val ibCheckFood = itemView.findViewById<ImageButton>(R.id.ibCheckFood)!!
        val ivPreviewImageFeed = itemView.findViewById<ImageView>(R.id.ivPreviewImageFeed)!!
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewPagerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.feed_item, parent, false)
        val ivPreviewImageFeed = view.findViewById<ImageView>(R.id.ivPreviewImageFeed)
        // ensure the image is a square

        ivPreviewImageFeed.adjustViewBounds = true
        ivPreviewImageFeed.scaleType = ImageView.ScaleType.CENTER_CROP
        return RecyclerViewPagerViewHolder(view)
    }

    override fun getItemCount(): Int {
        return feedList.size
    }

    public fun getFoodID(
        position: Int
    ): Int {
        if (position < 0 || position >= feedList.size)
            return -1;
        return feedList[position].feedId;
    }

    public fun getImageLink(position: Int): String {
        return feedList[position].feedImageUri
    }

    override fun onBindViewHolder(
        holder: RecyclerViewPagerViewHolder,
        position: Int
    ) {
        val feedInfo = feedList[position]

        holder.ivPreviewImageFeed.setBackgroundResource(R.drawable.loading_image);

        holder.txtUsernameFeed.text = feedInfo.feedUsername
        holder.txtCommentFeed.text = feedInfo.feedDescription
        holder.txtDateFeed.text = feedInfo.feedDate
        holder.ibCheckFood.setOnClickListener {
            listener.onItemClick(position)
        }
            // convert string to uri
        if (feedInfo.feedImageUri.startsWith("http")) {
            ImageUtilityClass
                .Companion
                .loadImageViewFromUrl(
                    holder.ivPreviewImageFeed,
                    feedInfo.feedImageUri
                )
        } else {
            val uri: Uri = Uri.parse(feedInfo.feedImageUri)
            //println("This is URI: $uri")
            holder.ivPreviewImageFeed.setImageURI(uri)
//        holder.ivPreviewImageFeed.layoutParams.height = holder.ivPreviewImageFeed.measuredWidth
        }

        // ensure ratio of image is 1:1
        holder.ivPreviewImageFeed.layoutParams.height = holder.ivPreviewImageFeed.measuredWidth
    }

}