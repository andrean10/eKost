package com.example.eKost.view.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.eKost.R
import com.example.eKost.data.UserPreferences
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val userPreferences = UserPreferences(requireActivity().applicationContext)
        val state = userPreferences.getUser().isValid

        if (state) {
            return inflater.inflate(R.layout.fragment_login, container, false)
        } else {
            return inflater.inflate(R.layout.fragment_login, container, false)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val loginFragmentDirections = LoginFragmentDirections.actionNavigationLoginToCheckLoginFragment()

        btnPencariKost.setOnClickListener {
            loginFragmentDirections.idLogin = 2
            view.findNavController().navigate(loginFragmentDirections)
        }

        btnPemilikKost.setOnClickListener {
            loginFragmentDirections.idLogin = 3
            view.findNavController().navigate(loginFragmentDirections)
        }
    }
}