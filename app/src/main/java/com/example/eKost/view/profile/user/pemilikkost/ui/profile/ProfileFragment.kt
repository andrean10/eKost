package com.example.eKost.view.profile.user.pemilikkost.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.eKost.R
import com.example.eKost.session.UserPreferences
import kotlinx.android.synthetic.main.fragment_profilepemilik.*

class ProfileFragment : Fragment() {

    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var userPreferences: UserPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        profileViewModel =
            ViewModelProvider(
                this,
                ViewModelProvider.NewInstanceFactory()
            ).get(ProfileViewModel::class.java)
        return inflater.inflate(R.layout.fragment_profilepemilik, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userPreferences = UserPreferences(requireActivity().applicationContext)
        val dataUser = userPreferences.getUser()

        tvUsername.text = dataUser.username
        tvEmail.text = dataUser.email
        tvPassword.text = dataUser.password
        tvAddress.text = dataUser.address
        tvNumberTelephone.text = dataUser.noTelp
    }
}