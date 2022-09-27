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


class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    override fun init() {

        val navController : NavController = Navigation.findNavController(this, R.id.main_fragment)

        val fagmentMagnger = supportFragmentManager
        val parentFragment  = fagmentMagnger.findFragmentById(R.id.main_fragment)

        navController.navigatorProvider.addNavigator(
            KeepStateNavigator(
                this,
                parentFragment!!.getChildFragmentManager(),
                R.id.main_fragment
            )

        )
        navController.setGraph(R.navigation.nav_main)

        val appBarConfiguration = AppBarConfiguration.Builder(
            R.id.homeNavFragment,
            R.id.searchNavFragment,
            R.id.bookmarkNavFragment,
        ).build()

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
        NavigationUI.setupWithNavController(binding.bottomNavigation, navController)

        binding.bottomNavigation.setOnNavigationItemSelectedListener(object :
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