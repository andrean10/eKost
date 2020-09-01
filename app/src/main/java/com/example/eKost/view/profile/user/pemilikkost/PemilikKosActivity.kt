package com.example.eKost.view.profile.user.pemilikkost

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.eKost.MainActivity
import com.example.eKost.R
import com.example.eKost.session.UserPreferences
import com.google.android.material.navigation.NavigationView

class PemilikKosActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private var exit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pemilik_kos)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_profile
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // exit activity pemilikkos
        val navLogOutItem = navView.menu.findItem(R.id.nav_logout)
        navLogOutItem.setOnMenuItemClickListener(object : MenuItem.OnMenuItemClickListener {
            override fun onMenuItemClick(item: MenuItem?): Boolean {
                UserPreferences(this@PemilikKosActivity).apply {
                    removeIdUser()
                    removeLoginCode()
                    removeUser()
                }

                val intent = Intent(this@PemilikKosActivity, MainActivity::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                startActivity(intent)
                return false
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        super.onBackPressed()

        if (exit) {
            finish()
        } else {
            Toast.makeText(this, "Tekan lagi jika ingin keluar", Toast.LENGTH_SHORT).show()
            exit = true
            Handler().postDelayed({
                exit = false
            }, 3 * 1000.toLong())
        }
    }
}