package com.example.eKost.view.login

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.eKost.R
import com.example.eKost.network.NetworkConnection
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_form.*

class FormActivity : AppCompatActivity() {
    private var isFirstConnected = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        val networkConnection = NetworkConnection(this)
        networkConnection.observe(this, { isConnected ->
            showSnackBar(isConnected)
        })

        val mFragmentManager = supportFragmentManager
        val mLoginFragment = LoginFragment()
        val fragment = mFragmentManager.findFragmentByTag(LoginFragment::class.simpleName)

        if (fragment !is LoginFragment) {
            mFragmentManager
                .beginTransaction()
                .add(R.id.frame_container, mLoginFragment, LoginFragment::class.simpleName)
                .commit()
        }
    }

    fun showSnackBar(state: Boolean) {
        if (isFirstConnected) {
            if (!state) {
                val message = "Tidak Terhubung Ke Internet"

                Snackbar.make(fabMessage, message, Snackbar.LENGTH_LONG).show()

                isFirstConnected = false
            }
        } else {
            if (state) {
                val message = "Kembali Online"
                val color = Color.parseColor("#FF67CC12")

                Snackbar.make(fabMessage, message, Snackbar.LENGTH_LONG)
                    .setBackgroundTint(color).show()

            } else {
                val message = "Tidak Terhubung Ke Internet"

                Snackbar.make(fabMessage, message, Snackbar.LENGTH_LONG).show()
            }
        }

    }

}