package com.example.counterapp.Pages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.counterapp.GalleryPageAdapter
import com.example.epicture.EpictureApplication
import com.example.epicture.R
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.search_page.*
import kotlinx.android.synthetic.main.search_page.view.*


class SearchFragment : Fragment() {

    private lateinit var pagerAdapter : GalleryPageAdapter
    private lateinit var searchView : View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        searchView = inflater.inflate(R.layout.search_page, container, false)

        pagerAdapter = GalleryPageAdapter(activity!!.supportFragmentManager)
        searchView.searchViewPager.adapter = pagerAdapter
        searchView.searchViewPager.currentItem = 1
        searchView.recycler_tab_layout.setUpWithViewPager(searchView.searchViewPager)
        return (searchView)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initSearchBar()
    }

    private fun initSearchBar() {
        search_bar.queryHint = "Search on Epicture..."
        search_bar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                EpictureApplication.searchQuery = query
                pagerAdapter.notifyDataSetChanged()
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
    }
}
