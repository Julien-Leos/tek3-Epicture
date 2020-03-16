package com.example.counterapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.volley.Request
import com.example.counterapp.Image.ImageAdapter
import com.example.counterapp.Image.PhotoList
import com.example.epicture.EpictureApplication
import com.example.epicture.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_image.*
import kotlinx.android.synthetic.main.gallery_view.*
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

class ImageActivity : AppCompatActivity() {

    companion object {
        const val IMAGE_ID : String = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)

        fetchImageData()
    }

    fun fetchImageData() {
        val settings : MutableMap<String, String> = EpictureApplication.settings
        var imageId : String? = intent.getStringExtra("IMAGE_ID")
        imageId = "es1dwwh"

        val networkManager = NetworkManager(EpictureApplication.context)
        val urlSuffix = "3/gallery/album/$imageId"
        val header = mutableMapOf("Authorization" to "Client-ID " + settings["client_id"])

        networkManager.sendRequest(Request.Method.GET, urlSuffix, header, mutableMapOf(), { response ->
            val imageDetail = response.getJSONObject("data")
            val image : JSONObject = imageDetail.getJSONArray("images").getJSONObject(0)

            Picasso.get()
                .load("https://i.imgur.com/" + image.getString("id") + ".jpg")
                .into(image_image)

            image_title.text = imageDetail.getString("title")
            image_details_views.text = image.getString("views") + " views"

            if (image.getBoolean("favorite")) {
                heart_image.setImageResource(R.drawable.full_heart)
            }
        })
    }

}
