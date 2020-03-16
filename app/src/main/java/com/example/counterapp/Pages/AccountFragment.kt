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
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.account_page.*
import org.json.JSONArray

class AccountFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.account_page, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        fetchAccountData()
        initView()
    }

    private fun fetchAccountData() {
        val settings : MutableMap<String, String> = EpictureApplication.settings

        val networkManager = NetworkManager(EpictureApplication.context)
        var urlSuffix = "3/account/" + settings["account_username"]
        var header = mutableMapOf("Authorization" to "Client-ID " + settings["client_id"])

        networkManager.sendRequest(Request.Method.GET, urlSuffix, header, mutableMapOf(), { response ->
            user_name.text = response.getJSONObject("data").getString("url")
            user_details.text = response.getJSONObject("data").getString("reputation_name")

            Picasso.get()
                .load("https://imgur.com/user/" + settings["account_username"] + "/avatar?maxwidth=290.jpg")
                .into(user_profile_picture)
        })
    }

    private fun initView() {
        accountImageRecycler.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        val imageListAdapter = ImageAdapter()
        accountImageRecycler.adapter =imageListAdapter
        fetchAccountPictures()
    }


    private fun fetchAccountPictures() {
        val settings : MutableMap<String, String> = EpictureApplication.settings

        val networkManager = NetworkManager(EpictureApplication.context)
        val urlSuffix =  "3/account/me/images"
        val header = mutableMapOf("Authorization" to "Bearer " + settings["access_token"])

        networkManager.sendRequest(Request.Method.GET, urlSuffix, header, mutableMapOf(), { response ->
            val albumList : JSONArray = response.getJSONArray("data")
            val photoList = PhotoList()
            photoList.parseImage(albumList,false)
            val imageListAdapter = accountImageRecycler.adapter as ImageAdapter
            imageListAdapter.setPhotoList(photoList.getList())
        })
    }
}