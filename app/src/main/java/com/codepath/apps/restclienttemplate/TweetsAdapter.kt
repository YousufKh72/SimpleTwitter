package com.codepath.apps.restclienttemplate

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codepath.apps.restclienttemplate.models.Tweet

class TweetsAdapter(val tweets: ArrayList<Tweet>) : RecyclerView.Adapter<TweetsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)

        //Inflate our item layout
        val view = inflater.inflate(R.layout.item_tweet, parent, false)

        return ViewHolder(view)
    }

 //Populating data into the item through holder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       // Get the data model based on the position
        val tweet: Tweet = tweets.get(position)

     // Set item views based on the views and data model
        var formattedTime = TimeFormatter.getTimeDifference(tweet.createdAt)

     //Set item views based on views and data model

     holder.tvUserName.text = tweet.user?.name
     holder.tvTweetBody.text = tweet.body
     holder.tvTimeStamp.text = formattedTime

     Glide.with(holder.itemView).load(tweet.user?.publicImageUrl).into(holder.ivProfileImage)
    }

    override fun getItemCount(): Int {
        return tweets.size
    }

    fun clear() {
        tweets.clear()
        notifyDataSetChanged()
    }

    // Add a list of items -- change to type used
    fun addAll(tweetList: List<Tweet>) {
        tweets.addAll(tweetList)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val ivProfileImage = itemView.findViewById<ImageView>(R.id.ivProfileImage)
        val tvUserName = itemView.findViewById<TextView>(R.id.tvUserName)
        val tvTweetBody = itemView.findViewById<TextView>(R.id.tvTweetBody)
        val tvTimeStamp = itemView.findViewById<TextView>(R.id.tvTimeStamp)



    }
}
