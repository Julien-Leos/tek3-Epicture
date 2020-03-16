package com.example.counterapp.Image

import android.widget.ImageView
import android.widget.TextView
import org.json.JSONArray

class PhotoVH {
    var photo: ImageView? = null
    var title: TextView? = null
    var score: TextView? = null
    var id: String? = null
    var textTitle: String? = null
    var textScore: String? = null
    var canBeFavorite : Boolean? = false
    var isFavorite: Boolean? = false
}

class PhotoList {
    private val list : MutableList<PhotoVH> = arrayListOf()

    fun addPicture(id: String, title: String, score: String, canBefavorite: Boolean, isFavorite: Boolean) {
        val photo = PhotoVH()
        photo.id = id
        photo.textTitle = title
        photo.textScore = score
        photo.canBeFavorite = canBefavorite
        photo.isFavorite = isFavorite
        list.add(photo)
    }

    fun getList(): MutableList<PhotoVH> {
        return (this.list)
    }

    fun parseGallery(albumList: JSONArray) {
        for (i in 0 until albumList.length()) {
            val imagesNb : Boolean =  albumList.getJSONObject(i).getBoolean("is_album")
            if (!imagesNb) {
                continue
            } else {
                val imageList: JSONArray = albumList.getJSONObject(i).getJSONArray("images")
                val title : String = albumList.getJSONObject(i).getString("title")
                val score : String = albumList.getJSONObject(i).getString("views")
                addPicture(imageList.getJSONObject(0).getString("id"), title, score,
                    canBefavorite = true,
                    isFavorite = imageList.getJSONObject(0).getBoolean("favorite")
                )
            }
        }
    }

    fun parseImage(imageList: JSONArray, favorite: Boolean){
        for (i in 0 until imageList.length()) {
            val title : String = imageList.getJSONObject(i).getString("title")
            val score : String = imageList.getJSONObject(i).getString("views")
            addPicture(imageList.getJSONObject(i).getString("id"), title, score,
                canBefavorite = favorite,
                isFavorite = favorite
            )
        }
    }
}
