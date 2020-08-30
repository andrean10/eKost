package com.example.eKost

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.eKost.network.NetworkConnection
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        navView.setupWithNavController(navController)

        val networkConnection = NetworkConnection(applicationContext)
        networkConnection.observe(this, { isConnected ->
            showSnackBar(isConnected)
        })
    }

    fun showSnackBar(state: Boolean) {
        if (!state) {
            val message = "Tidak Terhubung Ke Internet"

            val snackbar = Snackbar.make(fabMessage, message, Snackbar.LENGTH_LONG)
                .setAnchorView(R.id.nav_view)
            snackbar.show()
        }
    }
}