package com.example.eKost.view.profile.user.setting

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.eKost.MainActivity
import com.example.eKost.R
import com.example.eKost.session.UserPreferences
import com.example.eKost.view.profile.user.edit.EditProfileFragment
import kotlinx.android.synthetic.main.fragment_settings.*

@Suppress("DEPRECATION")
class SettingsFragment : Fragment() {
    private lateinit var userPreferences: UserPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnEditProfile.setOnClickListener {

            val mFragmentManager = fragmentManager
            val mEditProfileFragment = EditProfileFragment()
            mFragmentManager?.beginTransaction()
                ?.replace(
                    R.id.frame_container,
                    mEditProfileFragment,
                    EditProfileFragment::class.simpleName
                )
                ?.addToBackStack(null)
                ?.commit()
        }

        btnLogOut.setOnClickListener {
            userPreferences = UserPreferences(requireActivity().applicationContext)
            userPreferences.removeIdUser()
            userPreferences.removeLoginState()
            userPreferences.removeLoginCode()

            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
        }
    }
}