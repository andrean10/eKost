package com.example.eKost.view.login

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.eKost.data.UserPreferences
import com.example.eKost.data.model.User
import com.example.eKost.model.datalogin.ResponseLogin
import com.example.eKost.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {

    companion object {
        private val TAG = LoginViewModel::class.simpleName
    }

    private lateinit var dataLogin: MutableLiveData<ResponseLogin>

    fun getLogin(
        context: Context?,
        email: String,
        password: String,
        codeLogin: Int
    ): LiveData<ResponseLogin> {
        dataLogin = MutableLiveData()

        when (codeLogin) {
            2 -> dataLoginUser(context, email, password, codeLogin)
            3 -> dataLoginPemilikKost(context, email, password, codeLogin)
        }
        return dataLogin
    }

    private fun dataLoginUser(context: Context?, email: String, password: String, codeLogin: Int) {
        val client = ApiConfig.getApiService().getListUserKost(email, password, codeLogin)

        client.enqueue(object : Callback<ResponseLogin> {
            override fun onResponse(call: Call<ResponseLogin>, response: Response<ResponseLogin>) {
                val result = response.body()
                val status = result?.status?.toInt()
                val message = result?.message
                // data idHakAkses
                val idHakAkses = result?.data?.idHakakses?.toInt()

                Log.d(TAG, "onResponse: $idHakAkses")

                lateinit var userPreferences: UserPreferences
                if (context != null) {
                    userPreferences = UserPreferences(context)
                }

                // cek status keberhasilan checklogin dengan code
                when (status) {
                    2 -> {
                        // jika berhasil update data ke userpreferences
                        userPreferences.setUser(User(email, password, true))
//                        userPreferences.setLoginCode(idHakAkses?: 0)
                        dataLogin.postValue(result)
                        Log.d(TAG, "onResponse: $message")
                    }
                    1 -> {
                        dataLogin.postValue(result)
                        Log.d(TAG, "onResponse: $message")
                    }
                    0 -> {
                        dataLogin.postValue(result)
                        Log.d(TAG, "onResponse: $message")
                    }
                }
            }

            override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
                dataLogin.postValue(null)
                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
                Log.d(TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun dataLoginPemilikKost(
        context: Context?,
        email: String,
        password: String,
        codeLogin: Int
    ) {
        val client = ApiConfig.getApiService().getListPemilikKost(email, password, codeLogin)

        client.enqueue(object : Callback<ResponseLogin> {
            override fun onResponse(call: Call<ResponseLogin>, response: Response<ResponseLogin>) {
                val result = response.body()
                val status = result?.status?.toInt()
                val message = result?.message

                // cek kondisi status login apakah berhasil atau tidak
                when (status) {
                    2 -> {
                        dataLogin.postValue(result)
                        Log.d(TAG, "onResponse: $message")
                    }
                    1 -> {
                        dataLogin.postValue(result)
                        Log.d(TAG, "onResponse: $message")
                    }
                    0 -> {
                        dataLogin.postValue(result)
                        Log.d(TAG, "onResponse: $message")
                    }
                }
            }

            override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
                dataLogin.postValue(null)
                Log.d(TAG, "onFailure: ${t.message}")
            }
        })
    }

//    private fun generateMd5(password: String): String? {
//        val bytepassword = password.toByteArray()
//        var md: MessageDigest? = null
//        try {
//            md = MessageDigest.getInstance("MD5")
//        } catch (e: NoSuchAlgorithmException) {
//            Logger.getLogger(CheckLoginFragment::class.java.getName()).log(Level.SEVERE, null, e)
//        }
//        assert(md != null)
//        val hasil = md!!.digest(bytepassword)
//
//        val sb = StringBuilder(hasil.size * 2)
//        for (i in hasil.indices) {
//            val j: Byte = hasil[i] and 0xff.toByte()
//            if (j < 16) {
//                sb.append('0')
//            }
//            sb.append(Integer.toHexString(j.toInt()))
//        }
//        return sb.toString()
//    }
}