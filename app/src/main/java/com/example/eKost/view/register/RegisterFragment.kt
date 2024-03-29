package com.example.eKost.view.register

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.eKost.R
import com.example.eKost.view.login.LoginFragment
import kotlinx.android.synthetic.main.fragment_register.*

@Suppress("DEPRECATION")
class RegisterFragment : Fragment() {

    private lateinit var registerViewModel: RegisterViewModel
    private val MUST_NOT_NULL = "Bidang Harus Di Isi"

    private val TAG = RegisterFragment::class.java.simpleName

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        registerViewModel =
            ViewModelProvider.NewInstanceFactory().create(RegisterViewModel::class.java)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showLoading(true)

        btnRegister.setOnClickListener {
            checkRegister()
        }

        tvLogin.setOnClickListener {
            val mFragmentManager = fragmentManager
            val mLoginFragment = LoginFragment()
            mFragmentManager?.beginTransaction()
                ?.replace(R.id.frame_container, mLoginFragment, LoginFragment::class.simpleName)
                ?.commit()
        }
        showLoading(false)
    }

    private fun checkRegister() {
        val inputUsername = edtName.text.toString().trim()
        val inputEmail = edtEmail.text.toString().trim()
        val inputPassword = edtPassword.text.toString().trim()
        val inputAddress = edtAddress.text.toString().trim()
        val inputNumberPhone = edtTelephone.text.toString().trim()

        when {
            inputUsername.isEmpty() -> edtName.error = MUST_NOT_NULL
            inputEmail.isEmpty() -> edtEmail.error = MUST_NOT_NULL
            inputPassword.isEmpty() -> edtPassword.error = MUST_NOT_NULL
            inputAddress.isEmpty() -> edtAddress.error = MUST_NOT_NULL
            inputNumberPhone.isEmpty() -> edtTelephone.error = MUST_NOT_NULL
            else -> {
                showLoading(true)
                registerViewModel.getRegister(
                    requireActivity().applicationContext, inputUsername,
                    inputEmail, inputPassword, inputAddress, inputNumberPhone
                ).observe(
                    viewLifecycleOwner, { resultRegister ->
                        if (resultRegister != null) {
                            val status = resultRegister.status
                            val message = resultRegister.message

                            when (status) {
                                2 -> {
                                    val mFragmentManager = fragmentManager
                                    val mLoginFragment = LoginFragment()
                                    mFragmentManager?.beginTransaction()
                                        ?.replace(
                                            R.id.frame_container, mLoginFragment,
                                            LoginFragment::class.simpleName
                                        )
                                        ?.commit()

                                    Toast.makeText(context, "$message", Toast.LENGTH_SHORT).show()
                                }
                                1 -> {
                                    Log.d(TAG, "checkRegister: $message")
                                    tiemail.isErrorEnabled = true
                                    tiemail.error = message
                                }
                                0 -> {
                                    Log.d(TAG, "checkRegister: $message")
                                    Toast.makeText(
                                        activity?.applicationContext, "$message",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                            showLoading(false)
                        } else {
                            showLoading(false)
                        }
                    })
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