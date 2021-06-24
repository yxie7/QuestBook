package com.neoahdev.questbook.ui

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.core.widget.NestedScrollView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.navigation.NavigationView
import com.neoahdev.questbook.R
import com.neoahdev.questbook.model.*
import com.neoahdev.questbook.util.FileManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.bottom_sheet_post_daily.*


class MainActivity : AppCompatActivity(), BottomSheetDailyFragment.DailyRefreshInterface {
    // this allows these objects to be used outside of this class
    companion object {
        var questLists: QuestLists = QuestLists(mutableListOf<DailyQuest>(), mutableListOf<WeeklyQuest>())
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

        fileManager = FileManager(applicationContext)
        questLists = fileManager.getQuestLists()

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
        val navController = findNavController(R.id.nav_host_fragment)
        appbar.setExpanded(true, true)
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
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
            newDailyClick()
        }

        fabNewWeekly.setOnClickListener {
            Toast.makeText(this, "new weekly", Toast.LENGTH_SHORT).show()
        }

        /*
        // Hide FAB only while scrolling, shows when stopped
        nsv.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener() { v: NestedScrollView, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->
            if(scrollY > oldScrollY) fab.hide()
            else fab.show()
        })*/
    }

    fun newDailyClick() {
        BottomSheetDailyFragment().apply {
            show(supportFragmentManager, BottomSheetDailyFragment.TAG)
        }
        onFabClicked()
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
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        val drawerLayout: DrawerLayout = drawer_layout
        val navView: NavigationView = nav_drawer
        val parallaxLayout = findViewById<CollapsingToolbarLayout>(R.id.collapsingToolbar)
        val toolbar: Toolbar = findViewById(R.id.toolbar)

        appBarConfiguration = AppBarConfiguration(setOf(R.id.nav_home, R.id.nav_daily, R.id.nav_weekly), drawerLayout)
        //appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)
        //appBarConfiguration = AppBarConfiguration.Builder(navController.graph).build()

        setSupportActionBar(toolbar)
        //setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        parallaxLayout.setupWithNavController(toolbar, navController, drawerLayout)
        toolbar.setupWithNavController(navController, appBarConfiguration)

    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START))
            drawer_layout.closeDrawer(GravityCompat.START)
        else super.onBackPressed()
    }

    override fun refreshDailyQuestList() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        val childFragmentManager = navHostFragment?.childFragmentManager
        var lf = childFragmentManager?.fragments
        if (lf != null) {
            for (frag in lf) {
                Log.d("z",frag.toString())
            }
        }
        val frag:DailyQuestFragment = lf?.get(0) as DailyQuestFragment
        frag.refreshDailyQuestList()
    }


}