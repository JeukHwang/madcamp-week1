package com.madcamp.week1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.madcamp.week1.databinding.ActivityMainBinding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.Adapter
import android.widget.ImageView

class MainActivity : AppCompatActivity() {

    var addList = arrayListOf<Info>(
        Info("Song", "https://github.com/minjisong128", "email_minji", "https://avatars.githubusercontent.com/u/110910757?v=4"),
        Info("Hwang", "https://github.com/JeukHwang", "email_jeuk", "https://avatars.githubusercontent.com/u/92910273?v=4"),
        Info("Kim", "https://github.com/kim", "email_kim", "https://avatars.githubusercontent.com/u/6163?v=4"),
        Info("Lee", "https://github.com/lee", "email_lee", "https://avatars.githubusercontent.com/u/2014?v=4"),
        Info("Park", "https://github.com/park", "email_park", "https://avatars.githubusercontent.com/u/135650?v=4"),
        Info("Choi", "https://github.com/choi", "email_choi", "https://avatars.githubusercontent.com/u/10793473?v=4"),
        Info("Lim", "https://github.com/lim", "email_lim", "https://avatars.githubusercontent.com/u/7571747?v=4")
    )

    private lateinit var binding: ActivityMainBinding
    private lateinit var mRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_profile
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}