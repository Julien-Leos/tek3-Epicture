package com.example.counterapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.android.volley.Request
import com.example.counterapp.Image.ImageAdapter
import com.example.counterapp.Image.PhotoList
import com.example.counterapp.NetworkManager
import com.example.epicture.EpictureApplication
import com.example.epicture.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.account_page.*
import kotlinx.android.synthetic.main.gallery_view.*
import kotlinx.android.synthetic.main.gallery_view.view.*
import org.json.JSONArray

class GalleryFragment(sort : String) : Fragment() {

    private val sort = sort

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return (inflater.inflate(R.layout.gallery_view, container, false))
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        fetchSearchData(sort)
    }

    private fun initView() {
        image_recycler.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        val imageListAdapter = ImageAdapter()
        image_recycler.adapter = imageListAdapter
    }

    private fun fetchSearchData(sort : String) {
        val settings : MutableMap<String, String> = EpictureApplication.settings

        val networkManager = NetworkManager(EpictureApplication.context)
        val urlSuffix = "3/gallery/search/$sort/?q=${EpictureApplication.searchQuery}"
        val header = mutableMapOf("Authorization" to "Client-ID " + settings["client_id"])

        networkManager.sendRequest(Request.Method.GET, urlSuffix, header, mutableMapOf(), { response ->
            val albumList : JSONArray = response.getJSONArray("data")
            val photoList = PhotoList()
            photoList.parseGallery(albumList)
            val imageListAdapter = image_recycler.getAdapter() as ImageAdapter
            imageListAdapter.setPhotoList(photoList.getList())
        })
    }
}