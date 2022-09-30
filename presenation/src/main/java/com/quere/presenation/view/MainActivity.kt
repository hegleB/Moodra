package com.quere.presenation.view

import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.quere.presenation.R
import com.quere.presenation.base.BaseActivity
import com.quere.presenation.databinding.ActivityMainBinding
import com.quere.presenation.utils.KeepStateNavigator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    override fun init() {

        val navController : NavController = Navigation.findNavController(this, R.id.fragment_main)

        val fagmentMagnger = supportFragmentManager
        val parentFragment  = fagmentMagnger.findFragmentById(R.id.fragment_main)

        navController.navigatorProvider.addNavigator(
            KeepStateNavigator(
                this,
                parentFragment!!.getChildFragmentManager(),
                R.id.fragment_main
            )

        )
        navController.setGraph(R.navigation.nav_main)

        val appBarConfiguration = AppBarConfiguration.Builder(
            R.id.homeFragment,
            R.id.searchNavFragment,
            R.id.bookmarkNavFragment,
        ).build()


        NavigationUI.setupWithNavController(binding.bottomNavigationMain, navController)

        binding.bottomNavigationMain.setOnNavigationItemSelectedListener(object :
            BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {

                if(item.isChecked) return false

                when(item.itemId){
                    R.id.homeFragment -> {navController.navigate(R.id.homeContainerFragment,null)}
                    R.id.searchFragment -> {navController.navigate(R.id.searchContainerFragment,null)}
                    R.id.bookmarkFragment -> {navController.navigate(R.id.bookmarkContainerFragment,null)}
                }
                return true
            }

        })


    }
}