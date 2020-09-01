package com.example.eKost.view.profile.user.edit

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.eKost.R
import com.example.eKost.session.UserPreferences
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.edit_profile_fragment.*


@Suppress("DEPRECATION")
class EditProfileFragment : Fragment(), View.OnClickListener {
    private lateinit var editProfileViewModel: EditProfileViewModel
    private val MUST_NOT_NULL = "Bidang Harus Di Isi"

    private val TAG = EditProfileFragment::class.java.simpleName

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        editProfileViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(EditProfileViewModel::class.java)
        return inflater.inflate(R.layout.edit_profile_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // set toolbar
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
        (activity as AppCompatActivity?)!!.supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = resources.getString(R.string.editProfile)
        }
        // set option menu
        setHasOptionsMenu(true)

        // ambil data dari userpreferences
        val userPreferences = UserPreferences(requireActivity().applicationContext)
        val dataUser = userPreferences.getUser()

        edtNameProfile.setText(dataUser.username)
        edtEmailProfile.setText(dataUser.email)
        edtPasswordProfile.setText(dataUser.password)
        edtAddressProfile.setText(dataUser.address)
        edtTelephoneProfile.setText(dataUser.noTelp)

        btnSaveProfile.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.btnSaveProfile) {
            val username = edtNameProfile.text.toString().trim()
            val email = edtEmailProfile.text.toString().trim()
            val password = edtPasswordProfile.text.toString().trim()
            val addres = edtAddressProfile.text.toString().trim()
            val noTelephone = edtTelephoneProfile.text.toString().trim()

            showLoading(true)

            when {
                username.isEmpty() -> edtNameProfile.error = MUST_NOT_NULL
                email.isEmpty() -> edtEmailProfile.error = MUST_NOT_NULL
                password.isEmpty() -> edtPasswordProfile.error = MUST_NOT_NULL
                addres.isEmpty() -> edtAddressProfile.error = MUST_NOT_NULL
                noTelephone.isEmpty() -> edtTelephoneProfile.error = MUST_NOT_NULL
                else -> {

                    editProfileViewModel.getEditProfile(
                        activity?.applicationContext as Context,
                        username, email, password, addres, noTelephone
                    ).observe(viewLifecycleOwner,
                        { state ->
                            if (state != null) {
                                if (state.kode == 1) {
                                    showMessage(state.message ?: "")
                                } else {
                                    showMessage(state.message ?: "")
                                }
                            }
                        })
                }
            }
            showLoading(false)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            if (fragmentManager?.backStackEntryCount != 0) {
                fragmentManager?.popBackStack()
            }
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showMessage(message: String) {
        Snackbar.make(fabMessage, message, Snackbar.LENGTH_LONG).show()
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }
}