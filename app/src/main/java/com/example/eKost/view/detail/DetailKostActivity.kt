package com.example.eKost.view.detail

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.eKost.R
import com.example.eKost.model.datakost.ResultsItem
import com.example.eKost.network.NetworkConnection
import kotlinx.android.synthetic.main.activity_detail_kost.*
import kotlinx.android.synthetic.main.content_detail_kost.*

class DetailKostActivity : AppCompatActivity() {
    private lateinit var detailViewModel: DetailViewModel
    private val URL_IMG = "http://192.168.43.42/eKost/gambarkost/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_kost)

        showLoading(true)

        // set toolbar
        setSupportActionBar(toolbar)

        val args: DetailKostActivityArgs by navArgs()
        val dataIdKost = args.idKost

        detailViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(DetailViewModel::class.java)

        val networkConnection = NetworkConnection(applicationContext)
        networkConnection.observe(this, { isConnected ->
            if (isConnected) {
                observerDataDetailKost(dataIdKost)
                showLoading(false)
            } else {
                showLoading(false)
            }
        })

        floatBtnBack.setOnClickListener { v ->
            if (v.id == R.id.floatBtnBack) {
                finish()
            }
        }
    }

    private fun observerDataDetailKost(dataIdKost: Int) {
        detailViewModel.getDataKost(applicationContext, dataIdKost)
            .observe(this, Observer { resultItems ->
                if (resultItems != null) {
                    prepare(resultItems)
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

    fun showLoading(state: Boolean) {
        if(state) {
            progressBarDetail.visibility = View.VISIBLE
        } else {
            progressBarDetail.visibility = View.GONE
        }
    }
}