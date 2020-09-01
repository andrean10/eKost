package com.example.eKost.view.login

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.eKost.model.datalogin.ResponseLoginPemilikKos
import com.example.eKost.model.datalogin.ResponseLoginUser
import com.example.eKost.network.ApiConfig
import com.example.eKost.session.Login
import com.example.eKost.session.User
import com.example.eKost.session.UserPreferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {

    private val TAG = LoginViewModel::class.simpleName

    private lateinit var dataLoginUser: MutableLiveData<ResponseLoginUser>
    private lateinit var dataLoginPemilikKos: MutableLiveData<ResponseLoginPemilikKos>
    private lateinit var userPreferences: UserPreferences

    fun getUserLogin(context: Context, email: String, password: String, codeLogin: Int)
            : LiveData<ResponseLoginUser> {

        dataLoginUser = MutableLiveData()
        dataLoginUser(context, email, password, codeLogin)
        return dataLoginUser
    }

    fun getPemilikKosLogin(context: Context, email: String, password: String, codeLogin: Int)
            : LiveData<ResponseLoginPemilikKos> {

        dataLoginPemilikKos = MutableLiveData()
        dataLoginPemilikKost(context, email, password, codeLogin)
        return dataLoginPemilikKos
    }

    private fun dataLoginUser(context: Context, email: String, password: String, codeLogin: Int) {
        val client = ApiConfig.getApiService().getListUserKost(email, password, codeLogin)

        client.enqueue(object : Callback<ResponseLoginUser> {
            override fun onResponse(
                call: Call<ResponseLoginUser>,
                responseUser: Response<ResponseLoginUser>
            ) {
                val result = responseUser.body()
                val status = result?.status?.toInt()
                val message = result?.message
                val dataUser = result?.data

                userPreferences = UserPreferences(context)

                // cek status keberhasilan checklogin dengan code
                when (status) {
                    2 -> {
                        // jika berhasil update data ke userpreferences
                        userPreferences.setIdUser(User(dataUser?.idUser))
                        userPreferences.setLoginState(Login(true))
                        dataLoginUser.postValue(result)
                        Log.d(TAG, "onResponse: $message")
                    }
                    1 -> {
                        dataLoginUser.postValue(result)
                        Log.d(TAG, "onResponse: $message")
                    }
                    0 -> {
                        dataLoginUser.postValue(result)
                        Log.d(TAG, "onResponse: $message")
                    }
                }
            }

            override fun onFailure(call: Call<ResponseLoginUser>, t: Throwable) {
                dataLoginUser.postValue(null)
                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
                Log.d(TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun dataLoginPemilikKost(
        context: Context, email: String, password: String,
        codeLogin: Int
    ) {

        val client = ApiConfig.getApiService().getListPemilikKost(email, password, codeLogin)

        client.enqueue(object : Callback<ResponseLoginPemilikKos> {
            override fun onResponse(
                call: Call<ResponseLoginPemilikKos>,
                responseUser: Response<ResponseLoginPemilikKos>
            ) {
                val result = responseUser.body()
                val status = result?.status?.toInt()
                val message = result?.message
                val dataUser = result?.data

                userPreferences = UserPreferences(context)

                // cek kondisi status login apakah berhasil atau tidak
                when (status) {
                    2 -> {
                        // jika berhasil update data ke userpreferences
                        userPreferences.setIdUser(User(dataUser?.idPemilikkos?.toInt()))
                        userPreferences.setUser(
                            User(
                                username = dataUser?.nama,
                                email = dataUser?.email,
                                password = dataUser?.password,
                                address = dataUser?.alamat,
                                noTelp = dataUser?.noTelp,
                            )
                        )
                        dataLoginPemilikKos.postValue(result)
                        Log.d(TAG, "onResponse: $message")
                    }
                    1 -> {
                        dataLoginPemilikKos.postValue(result)
                        Log.d(TAG, "onResponse: $message")
                    }
                    0 -> {
                        dataLoginPemilikKos.postValue(result)
                        Log.d(TAG, "onResponse: $message")
                    }
                }
            }

            override fun onFailure(call: Call<ResponseLoginPemilikKos>, t: Throwable) {
                dataLoginUser.postValue(null)
                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
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