package com.example.counterapp.Image

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.epicture.R


class ImageAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var listOfPhotos = listOf<PhotoVH>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ImageHolder(LayoutInflater.from(parent.context).inflate(R.layout.image_cardview, parent, false))
    }

    override fun getItemCount(): Int = listOfPhotos.size

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val imageViewHolder = viewHolder as ImageHolder
        imageViewHolder.bindView(listOfPhotos[position])
    }

    fun setPhotoList(list: List<PhotoVH>) {
        if (list.isNotEmpty()) {
            this.listOfPhotos = list
            notifyDataSetChanged()
        }

    }
}