package com.example.eKost.view.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.eKost.R
import kotlinx.android.synthetic.main.fragment_check_login.*

class CheckLoginFragment : Fragment() {

    private lateinit var loginViewModel: LoginViewModel
    private val MUST_NOT_NULL = "Bidang Harus Di Isi"

    private val TAG = CheckLoginFragment::class.java.simpleName

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loginViewModel = ViewModelProvider.NewInstanceFactory().create(LoginViewModel::class.java)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_check_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args: CheckLoginFragmentArgs by navArgs()
        val code = args.idLogin

        btnLogin.setOnClickListener {
            showLoading(true)
            loginCheck(code)
        }

        tvRegister.setOnClickListener {
            showLoading(true)
            val checkLoginFragmentDirections =
                CheckLoginFragmentDirections.actionCheckLoginFragmentToRegisterFragment()
            checkLoginFragmentDirections.idRegister = code
            view.findNavController().navigate(checkLoginFragmentDirections)
            showLoading(false)
        }

    }

    private fun loginCheck(code: Int) {
        val inputEmail = edtEmail.text.toString().trim()
        val inputPassword = edtPassword.text.toString().trim()

        when {
            inputEmail.isEmpty() -> edtEmail.error = MUST_NOT_NULL
            inputPassword.isEmpty() -> edtPassword.error = MUST_NOT_NULL
            else -> {
                loginViewModel.getLogin(requireActivity().applicationContext, inputEmail,
                    inputPassword, code).observe(viewLifecycleOwner, { resultLogin ->
                    Log.d(TAG, "loginCheck: Test")
                    // data ada
                    if (resultLogin != null) {
                        val status = resultLogin.status?.toInt()
                        val message = resultLogin.message

                        when (status) {
                            2 -> view?.findNavController()?.navigate(
                                R.id.action_checkLoginFragment_to_navigation_home
                            )
                            1 -> {
                                tvFailLogin.visibility = View.VISIBLE
                                tvFailLogin.text = message
                            }
                            0 -> {
                                tvFailLogin.visibility = View.VISIBLE
                                tvFailLogin.text = message
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