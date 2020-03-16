package com.example.counterapp.Pages

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
import kotlinx.android.synthetic.main.home_page.*
import org.json.JSONArray


class HomeFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.home_page, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
    }

    private fun initView() {
        homeImageRecycler.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        val imageListAdapter = ImageAdapter()
        homeImageRecycler.setAdapter(imageListAdapter)
        fetchPictures()
    }

    private fun fetchPictures() {
        val settings : MutableMap<String, String> = EpictureApplication.settings

        val networkManager = NetworkManager(EpictureApplication.context)
        val urlSuffix =  "3/gallery/search/?q=cat"
        val header = mutableMapOf("Authorization" to "Client-ID " + settings["client_id"])

        networkManager.sendRequest(Request.Method.GET, urlSuffix, header, mutableMapOf(), { response ->
            val albumList : JSONArray = response.getJSONArray("data")
            val photoList = PhotoList()
            photoList.parseGallery(albumList)
            val imageListAdapter = homeImageRecycler.adapter as ImageAdapter
            imageListAdapter.setPhotoList(photoList.getList())
        })
    }
}
