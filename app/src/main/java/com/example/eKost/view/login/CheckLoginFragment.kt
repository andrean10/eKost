package com.example.eKost.view.login

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.eKost.R
import kotlinx.android.synthetic.main.fragment_check_login.*

class CheckLoginFragment : Fragment() {

    private lateinit var loginViewModel: LoginViewModel
    private val MUST_NOT_NULL = "Bidang Harus Di Isi"

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
            loginCheck(code)
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
                    // data ada dan success
                    if (resultLogin != null) {
                        val idHakAkses = resultLogin.idHakakses?.toInt() as Int

                        when(idHakAkses) {
                            2 -> {
                                Toast.makeText(requireActivity().applicationContext,
                                    "Pencari Kost Berhasil Login", Toast.LENGTH_SHORT).show()
                            }
                            3 -> Toast.makeText(requireActivity().applicationContext, "Pemilik Kost Berhasil Login", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        // progressbar di letakkan disini
                    }
                })
            }
        }
    }
}