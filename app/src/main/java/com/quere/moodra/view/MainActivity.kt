package com.quere.moodra.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.*
import androidx.navigation.ui.*
import com.quere.moodra.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import dev.ilhamsuaib.navi.common.KeepStateNavigator
import kotlinx.android.synthetic.main.activity_main.*


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var doubleBackToExit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController : NavController = Navigation.findNavController(this, R.id.fragment)


        navController.navigatorProvider.addNavigator(
            KeepStateNavigator(
                this,
                fragment.getChildFragmentManager(),
                R.id.fragment
            )

        )
        navController.setGraph(R.navigation.nav_main)

        val appBarConfiguration = AppBarConfiguration.Builder(
            R.id.homeFragment,
            R.id.searchFragment,
            R.id.bookmarkFragment,
        ).build()

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
        NavigationUI.setupWithNavController(bottom_navigation, navController)

        bottom_navigation.setOnNavigationItemSelectedListener(object :
            BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {

                if(item.isChecked) return false

                when(item.itemId){
                    R.id.homeFragment -> {navController.navigate(R.id.homeFragment,null)}
                    R.id.searchFragment -> {navController.navigate(R.id.searchFragment,null)}
                    R.id.bookmarkFragment -> {navController.navigate(R.id.bookmarkFragment,null)}
                }
                return true
            }

        })


    }
}