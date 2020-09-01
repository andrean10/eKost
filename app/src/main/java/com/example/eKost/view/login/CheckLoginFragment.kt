package com.example.eKost.view.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.eKost.R
import com.example.eKost.session.UserPreferences
import kotlinx.android.synthetic.main.fragment_checklogin.*

class CheckLoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_checklogin, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userPreferences = UserPreferences(requireActivity().applicationContext)

        btnPencariKost.setOnClickListener {
            userPreferences.setLoginCode(2)
            view.findNavController().navigate(R.id.action_navigation_login_to_formActivity)
        }

        btnPemilikKost.setOnClickListener {
            userPreferences.setLoginCode(3)
            view.findNavController().navigate(R.id.action_navigation_login_to_formActivity)
        }
    }
}