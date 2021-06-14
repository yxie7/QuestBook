package com.neoahdev.questbook.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.navigation.NavigationView
import com.neoahdev.questbook.R
import com.neoahdev.questbook.model.DailyQuest
import com.neoahdev.questbook.model.QuestLists
import com.neoahdev.questbook.model.WeeklyQuest
import com.neoahdev.questbook.util.FileManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity() {
    // this allows these objects to be used outside of this class
    companion object {
        var questLists: QuestLists =
            QuestLists(mutableListOf<DailyQuest>(), mutableListOf<WeeklyQuest>())
        lateinit var fileManager: FileManager
        lateinit var appBarConfiguration: AppBarConfiguration
    }

    var clicked = false
    val rotateOpen: Animation by lazy {
        AnimationUtils.loadAnimation(
            this,
            R.anim.rotate_open_anim
        )
    }
    val rotateClose: Animation by lazy {
        AnimationUtils.loadAnimation(
            this,
            R.anim.rotate_close_anim
        )
    }
    val fromBottom: Animation by lazy {
        AnimationUtils.loadAnimation(
            this,
            R.anim.from_bottom_anim
        )
    }
    val toBottom: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.to_bottom_anim) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupFAB()
        setupActionBarAndDrawer()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        //menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        drawer_layout.openDrawer(GravityCompat.START)
        return true
    }

    fun setupFAB() {
        fab.setOnClickListener { view ->
            onFabClicked()
            Toast.makeText(this, "post", Toast.LENGTH_SHORT).show()
            /*
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()*/
        }
        fabNewDaily.setOnClickListener {
            Toast.makeText(this, "new daily", Toast.LENGTH_SHORT).show()
        }
        fabNewWeekly.setOnClickListener {
            Toast.makeText(this, "new weekly", Toast.LENGTH_SHORT).show()
        }
    }

    fun onFabClicked() {
        setFabVisibility(clicked)
        setFabAnimation(clicked)
        clicked = !clicked
    }

    fun setFabVisibility(clicked: Boolean) {
        if (!clicked) {
            fabNewDaily.visibility = View.VISIBLE
            fabNewWeekly.visibility = View.VISIBLE
        } else {
            fabNewDaily.visibility = View.GONE
            fabNewWeekly.visibility = View.GONE
        }
    }

    fun setFabAnimation(clicked: Boolean) {
        if (!clicked) {
            fabNewDaily.startAnimation(fromBottom)
            fabNewWeekly.startAnimation(fromBottom)
            fab.startAnimation(rotateOpen)
        } else {
            fabNewDaily.startAnimation(toBottom)
            fabNewWeekly.startAnimation(toBottom)
            fab.startAnimation(rotateClose)
        }
    }

    fun setupActionBarAndDrawer() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val drawerLayout: DrawerLayout = drawer_layout
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_daily, R.id.nav_weekly
            ), drawerLayout
        )
        val navView: NavigationView = findViewById(R.id.nav_drawer)

        appBarConfiguration = AppBarConfiguration(navController.graph,drawerLayout)

        val parallaxLayout = findViewById<CollapsingToolbarLayout>(R.id.collapsingToolbar)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setupWithNavController(navController,drawerLayout)

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

}