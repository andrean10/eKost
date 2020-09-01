package com.example.eKost.view.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.eKost.MainActivity
import com.example.eKost.R
import com.example.eKost.session.UserPreferences
import com.example.eKost.view.profile.user.pemilikkost.PemilikKosActivity
import com.example.eKost.view.register.RegisterFragment
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {
    private lateinit var loginViewModel: LoginViewModel
    private val MUST_NOT_NULL = "Bidang Harus Di Isi"

    private val TAG = LoginFragment::class.java.simpleName

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loginViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(LoginViewModel::class.java)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    @Suppress("DEPRECATION")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnLogin.setOnClickListener {
            showLoading(true)
            loginCheck()
        }

        tvRegister.setOnClickListener {
            val mFragmentManager = fragmentManager
            val mRegisterFragment = RegisterFragment()
            mFragmentManager?.beginTransaction()
                ?.replace(
                    R.id.frame_container,
                    mRegisterFragment,
                    RegisterFragment::class.simpleName
                )
                ?.addToBackStack(null)
                ?.commit()
        }
    }

    private fun loginCheck() {
        val inputEmail = edtEmail.text.toString().trim()
        val inputPassword = edtPassword.text.toString().trim()

        when {
            inputEmail.isEmpty() -> edtEmail.error = MUST_NOT_NULL
            inputPassword.isEmpty() -> edtPassword.error = MUST_NOT_NULL
            else -> {
                val userPreferences = UserPreferences(requireActivity().applicationContext)
                val codeLogin = userPreferences.getLoginCode()

                when (codeLogin) {
                    1 -> { // admin

                    }
                    2 -> { // user
                        loginViewModel.getUserLogin(
                            requireActivity().applicationContext, inputEmail,
                            inputPassword, codeLogin
                        ).observe(viewLifecycleOwner, { resultLogin ->
                            // data ada
                            if (resultLogin != null) {
                                // get data id akses
                                val idAkses = resultLogin.data?.idHakakses?.toInt()
                                val status = resultLogin.status?.toInt()
                                val message = resultLogin.message

                                Log.d(TAG, "loginCheck: $idAkses")

                                when (status) {
                                    2 -> {
                                        val intent = Intent(activity, MainActivity::class.java)
                                        startActivity(intent)
                                    }
                                    1 -> {
                                        tvFailLogin.visibility = View.VISIBLE
                                        tvFailLogin.text = message
                                    }
                                    0 -> {
                                        tvFailLogin.visibility = View.VISIBLE
                                        tvFailLogin.text = message
                                    }
                                }
                            }
                            showLoading(false)
                        })
                    }
                    3 -> { // pemilikkos
                        loginViewModel.getPemilikKosLogin(
                            requireActivity().applicationContext, inputEmail,
                            inputPassword, codeLogin
                        ).observe(viewLifecycleOwner, { resultLogin ->
                            if (resultLogin != null) {
                                // get data id akses
                                val idAkses = resultLogin.data?.idHakakses?.toInt()
                                val status = resultLogin.status?.toInt()
                                val message = resultLogin.message

                                Log.d(TAG, "loginCheck: $idAkses")

                                when (status) {
                                    2 -> {
                                        // akan mengeluarkan langsung aplikasi
                                        val intent =
                                            Intent(activity, PemilikKosActivity::class.java).apply {
                                                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                                addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                                            }
                                        startActivity(intent)
                                    }
                                    1 -> {
                                        tvFailLogin.visibility = View.VISIBLE
                                        tvFailLogin.text = message
                                    }
                                    0 -> {
                                        tvFailLogin.visibility = View.VISIBLE
                                        tvFailLogin.text = message
                                    }
                                }
                            }
                            showLoading(false)
                        })
                    }
                }
            }
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }
}