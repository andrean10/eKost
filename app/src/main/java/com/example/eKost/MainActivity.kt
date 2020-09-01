package com.example.eKost

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.eKost.network.NetworkConnection
import com.example.eKost.session.UserPreferences
import com.example.eKost.view.profile.user.pemilikkost.PemilikKosActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val TAG = MainActivity::class.java.simpleName
    private var state: Boolean = false
    private lateinit var stateViewModel: StateViewModel
    lateinit var navView: BottomNavigationView
    lateinit var navController: NavController
    lateinit var userPreferences: UserPreferences
    private var isFirstConnected = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        userPreferences = UserPreferences(this)
        // cek activity jika loginnya pemilik kos maka langsung di arahkan ke halaman pemilik kost
        if (userPreferences.getLoginCode() == 3) {
            val intent = Intent(this, PemilikKosActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            }
            startActivity(intent)
        }

        Log.d(TAG, "onCreate: ${userPreferences.getLoginCode()}")

        navView = findViewById(R.id.nav_view)
        navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        state = userPreferences.getLoginState().isValid

        stateViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(StateViewModel::class.java)

        // observe
        // step 1
        stateViewModel.getState(this).observe(this, { state ->
            if (state) {
                navView.menu.findItem(R.id.navigation_login).isVisible = false
                navView.menu.findItem(R.id.navigation_profile).isVisible = true
            } else {
                navView.menu.findItem(R.id.navigation_login).isVisible = true
                navView.menu.findItem(R.id.navigation_profile).isVisible = false
            }
        })

        navView.setupWithNavController(navController)

        val networkConnection = NetworkConnection(applicationContext)
        networkConnection.observe(this, { isConnected ->
            showSnackBar(isConnected)
        })
    }

    fun showSnackBar(state: Boolean) {

        if (isFirstConnected) {
            if (!state) {
                val message = "Tidak Terhubung Ke Internet"

                Snackbar.make(fabMessage, message, Snackbar.LENGTH_LONG)
                    .setAnchorView(R.id.nav_view).show()

                isFirstConnected = false
            }
        } else {
            if (state) {
                val message = "Kembali Online"
                val color = Color.parseColor("#FF67CC12")

                Snackbar.make(fabMessage, message, Snackbar.LENGTH_LONG)
                    .setBackgroundTint(color)
                    .setAnchorView(R.id.nav_view).show()

            } else {
                val message = "Tidak Terhubung Ke Internet"

                Snackbar.make(fabMessage, message, Snackbar.LENGTH_LONG)
                    .setAnchorView(R.id.nav_view).show()
            }
        }

    }
}