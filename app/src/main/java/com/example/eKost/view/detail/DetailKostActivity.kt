package com.example.eKost.view.detail

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.eKost.R
import com.example.eKost.model.datakost.ResultsItem
import com.example.eKost.network.NetworkConnection
import com.example.eKost.session.UserPreferences
import kotlinx.android.synthetic.main.activity_detail_kost.*
import kotlinx.android.synthetic.main.content_detail_kost.*

class DetailKostActivity : AppCompatActivity() {
    private lateinit var detailViewModel: DetailViewModel
    private val URL_IMG = "http://192.168.43.42/eKost/gambarkost/"
    private lateinit var userPreferences: UserPreferences
    private var state: Boolean = false

    private val TAG = DetailKostActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_kost)

        showLoading(true)
        setSupportActionBar(toolbar)

        userPreferences = UserPreferences(this)
        // ambil data user login
        state = userPreferences.getLoginState().isValid

        // ambil data args dari kostfragment
        val args: DetailKostActivityArgs by navArgs()
        val resultIdKost = args.idKost

        detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(DetailViewModel::class.java)

        val networkConnection = NetworkConnection(applicationContext)
        networkConnection.observe(this, { isConnected ->
            if (isConnected) {
                observerDataDetailKost(resultIdKost)
                showLoading(false)
            } else {
                showLoading(false)
            }
        })

        // button kembali
        floatBtnBack.setOnClickListener { v ->
            if (v.id == R.id.floatBtnBack) {
                finish()
            }
        }

    }

    // ambil data detailkost
    private fun observerDataDetailKost(idKost: Int?) {
        detailViewModel.getDataKost(this, idKost)
            .observe(this, { resultItems ->
                if (resultItems != null) {
                    prepare(resultItems)

                    lateinit var dataNoTelephone: String
                    resultItems.forEach {
                        dataNoTelephone = it.noTelp.toString()
                    }

                    // jika true lanjut ke api order
                    if (state) {
                        btnOrder.setOnClickListener {
                            val number = dataNoTelephone.substring(1)
                            // order ke via whatsapp api
                            openWA("+62 $dataNoTelephone")

                            Log.d(TAG, "observerDataDetailKost: $number")
                        }
                    } else {
                        btnOrder.isEnabled = false
                        btnOrder.text = resources.getString(R.string.alert_login)
                    }
                }
                showLoading(false)
            })
    }

    private fun prepare(resultItems: ArrayList<ResultsItem>) {
        resultItems.forEach { result ->
            Glide.with(applicationContext as Context)
                .load(URL_IMG + result.gambar)
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.img_not_found)
                        .error(R.drawable.img_not_found)
                )
                .into(imageDetailKost)
            namePemilikKost.text = result.namakos
            numberTelephone.text = result.noTelp
            addressHouse.text = result.alamat
            priceHouse.text = result.harga
            categoryHouse.text = result.kategori
            descriptionHouse.text = result.deskripsi
            totalBoarding.text = result.jumlahKos
        }
    }

    private fun openWA(noTelephone: String) {
        Log.d(TAG, "openWA: $noTelephone")
        val resultNoHp = noTelephone.substring(1)
        Log.d(TAG, "openWA: $noTelephone")

        val url = "https://api.whatsapp.com/send?phone=$resultNoHp"
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(url)
        }
        startActivity(intent)
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressBarDetail.visibility = View.VISIBLE
        } else {
            progressBarDetail.visibility = View.GONE
        }
    }
}