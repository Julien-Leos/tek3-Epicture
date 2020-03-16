package com.example.counterapp

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter
import java.util.*
import android.R.string.no
import com.example.epicture.EpictureApplication
import kotlin.collections.ArrayList


class GalleryPageAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val filterTitle = arrayOf("Newest", "Viral", "Top")
    private val filterValue = arrayOf("time", "viral", "top")

    override fun getItem(position: Int) : Fragment {
        return GalleryFragment(filterValue[position])
    }

    override fun getCount() : Int {
        return filterTitle.size
    }

    override fun getPageTitle(position: Int) : CharSequence {
        return filterTitle[position]
    }

    override fun getItemPosition(item: Any): Int {
        return POSITION_NONE
    }
}