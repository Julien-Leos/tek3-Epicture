package com.example.epicture

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.counterapp.Pages.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        EpictureApplication.context = applicationContext
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        if (savedInstanceState == null) {
            val fragment = HomeFragment()
            supportFragmentManager.beginTransaction().replace(R.id.container, fragment, "HOME_FRAGMENT").commit()
        }
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
        when (menuItem.itemId) {
            R.id.navigation_home -> {
                val fragment = HomeFragment()
                supportFragmentManager.beginTransaction().replace(R.id.container, fragment, "HOME_FRAGMENT").commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_search -> {
                val fragment = SearchFragment()
                supportFragmentManager.beginTransaction().replace(R.id.container, fragment, "SEARCH_FRAGMENT").commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_upload -> {
                val fragment = UploadFragment()
                supportFragmentManager.beginTransaction().replace(R.id.container, fragment, "UPLOAD_FRAGMENT").commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_favorites -> {
                val fragment = FavoritesFragment()
                supportFragmentManager.beginTransaction().replace(R.id.container, fragment, "FAVORITE_FRAGMENT").commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_account -> {
                val fragment = AccountFragment()
                supportFragmentManager.beginTransaction().replace(R.id.container, fragment, "ACCOUNT_FRAGMENT").commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    fun disconnectRedirection() {
        val welcomeIntent = Intent(EpictureApplication.context, WelcomeActivity::class.java)
        startActivity(welcomeIntent)
    }

}
