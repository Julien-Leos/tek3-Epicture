package com.example.epicture

import com.example.counterapp.Image.ImageAdapter
import com.example.counterapp.Image.PhotoList
import com.example.counterapp.Image.PhotoVH
import com.example.counterapp.Pages.HomeFragment
import org.junit.Test
import org.junit.Assert.*


class UnitTest {
    @Test
    fun Empty_ImageAdapter() {
        val adapter = ImageAdapter()
        assertEquals(0, adapter.itemCount)
    }
    @Test
    fun EmptyList_ImageAdapter() {
        val photoList : MutableList<PhotoVH> = arrayListOf()
        val adapter = ImageAdapter()
        adapter.setPhotoList(photoList)
        assertEquals(0, adapter.itemCount)
    }
    @Test
    fun Add_Image_PhotoList() {
        val photoList = PhotoList()
        photoList.addPicture("id", "title", "2",true)
        assertEquals(false, photoList.getList().isEmpty())
    }
    @Test
    fun parseUrl_HasError() {
        val page = LoginActivity()
        assertEquals(false, page.parseUrl("https://api.imgur.com/access_denied"))
    }
}
