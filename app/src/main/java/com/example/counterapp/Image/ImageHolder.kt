package com.example.counterapp.Image

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.example.counterapp.ImageActivity
import com.example.counterapp.NetworkManager
import com.example.epicture.EpictureApplication
import com.example.epicture.EpictureApplication.Companion.context
import com.example.epicture.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.image_cardview.view.*

class ImageHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    lateinit var image : PhotoVH

    fun bindView(image: PhotoVH) {
        image.photo = itemView.findViewById(R.id.photo)
        image.title = itemView.findViewById(R.id.title)
        image.score = itemView.findViewById(R.id.score)

        Picasso.get()
            .load("https://i.imgur.com/" + image.id + ".jpg")
            .into(image.photo)

        image.title?.text = image.textTitle
        image.score?.text = image.textScore + " views"

        image.photo?.setOnClickListener{
            val imageIntent = Intent(context, ImageActivity::class.java)
            println("IMAGE ID: ")
            println(image.id)
            imageIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            imageIntent.putExtra(ImageActivity.IMAGE_ID, image.id)
            context.startActivity(imageIntent)
        }

        this.image = image

        if (image.canBeFavorite == true) {
            val heart = itemView.findViewById<ImageView>(R.id.heart)
            if (image.isFavorite == true) {
                heart.setImageResource(R.drawable.full_heart)
            } else {
                heart.setImageResource(R.drawable.empty_heart)
            }

            val up = itemView.findViewById<ImageView>(R.id.up)
            up.setImageResource(R.drawable.empty_up)

            val down = itemView.findViewById<ImageView>(R.id.down)
            down.setImageResource(R.drawable.empty_down)

            itemView.setOnLongClickListener{
                updateFavorite()
                return@setOnLongClickListener true
            }
            heart.setOnClickListener{
                updateFavorite()
            }
            up.setOnClickListener{
                voteUp()
            }
            down.setOnClickListener{
                voteDown()
            }
        }
    }

    private fun updateFavorite() {
        val settings : MutableMap<String, String> = EpictureApplication.settings
        val networkManager = NetworkManager(EpictureApplication.context)
        val urlSuffix =  "3/image/${this.image.id}/favorite"
        val header = mutableMapOf("Authorization" to "Bearer " + settings["access_token"])

        networkManager.sendRequest(Request.Method.POST, urlSuffix, header, mutableMapOf())
        image.isFavorite = !image.isFavorite!!
        if (image.isFavorite == true) {
            itemView.findViewById<ImageView>(R.id.heart).setImageResource(R.drawable.full_heart)
        } else {
            itemView.findViewById<ImageView>(R.id.heart).setImageResource(R.drawable.empty_heart)
        }
    }

    private fun voteUp() {
        val settings : MutableMap<String, String> = EpictureApplication.settings
        val networkManager = NetworkManager(context)
        val urlSuffix =  "3/gallery/${this.image.id}/vote/up"
        val header = mutableMapOf("Authorization" to "Bearer " + settings["access_token"])

        networkManager.sendRequest(Request.Method.POST, urlSuffix, header, mutableMapOf())
        itemView.findViewById<ImageView>(R.id.up).setImageResource(R.drawable.full_up)
    }

    private fun voteDown() {
        val settings : MutableMap<String, String> = EpictureApplication.settings
        val networkManager = NetworkManager(context)
        val urlSuffix =  "3/gallery/${this.image.id}/vote/down"
        val header = mutableMapOf("Authorization" to "Bearer " + settings["access_token"])

        networkManager.sendRequest(Request.Method.POST, urlSuffix, header, mutableMapOf())
        itemView.findViewById<ImageView>(R.id.down)?.setImageResource(R.drawable.full_down)
    }
}