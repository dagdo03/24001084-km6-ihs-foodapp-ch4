package com.example.foodapp.presentation.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.load
import coil.transform.CircleCropTransformation
import com.example.foodapp.R
import com.example.foodapp.data.datasource.auth.AuthDataSource
import com.example.foodapp.data.datasource.auth.FirebaseAuthDataSource
import com.example.foodapp.data.repository.UserRepository
import com.example.foodapp.data.repository.UserRepositoryImpl
import com.example.foodapp.data.source.firebase.FirebaseService
import com.example.foodapp.data.source.firebase.FirebaseServiceImpl
import com.example.foodapp.databinding.FragmentProfileBinding
import com.example.foodapp.presentation.login.LoginActivity
import com.example.foodapp.presentation.main.MainActivity
import com.example.foodapp.utils.GenericViewModelFactory
import com.example.foodapp.utils.proceedWhen


class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val viewModel: ProfileViewModel by viewModels {
        val s: FirebaseService = FirebaseServiceImpl()
        val ds: AuthDataSource = FirebaseAuthDataSource(s)
        val r: UserRepository = UserRepositoryImpl(ds)
        GenericViewModelFactory.create(ProfileViewModel(r))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showUserData()
        setClickListener()
        observeEditMode()
    }

    private fun doEditProfile(){
        if(checkNameValidation()){
            val fullName = binding.layoutProfileBody.nameEditText.text.toString().trim()
            val email = binding.layoutProfileBody.emailEditText.text.toString().trim()
            proceedEdit(fullName, email)
        }
    }
    private fun proceedEdit(fullName: String, email : String) {
        viewModel.updateProfileName(fullName = fullName).observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
                    binding.layoutProfileBody.pbLoading.isVisible = false
                    binding.layoutProfileBody.btnChangeProfile.isVisible = true
                    Toast.makeText(requireContext(), "Change Profile data Success !", Toast.LENGTH_SHORT).show()
                    showUserData()
                },
                doOnError = {
                    binding.layoutProfileBody.pbLoading.isVisible = false
                    binding.layoutProfileBody.btnChangeProfile.isVisible = true
                    Toast.makeText(requireContext(), "Change Profile data Failed !", Toast.LENGTH_SHORT).show()

                },
                doOnLoading = {
                    binding.layoutProfileBody.pbLoading.isVisible = true
                    binding.layoutProfileBody.btnChangeProfile.isVisible = false
                }
            )
        }
        viewModel.updateProfileEmail(email = email).observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
                    binding.layoutProfileBody.pbLoading.isVisible = false
                    binding.layoutProfileBody.btnChangeProfile.isVisible = true
                    Toast.makeText(requireContext(), "Change Profile data Success !", Toast.LENGTH_SHORT).show()
                    showUserData()
                },
                doOnError = {
                    binding.layoutProfileBody.pbLoading.isVisible = false
                    binding.layoutProfileBody.btnChangeProfile.isVisible = true
                    Toast.makeText(requireContext(), "Change Profile data Failed !", Toast.LENGTH_SHORT).show()

                },
                doOnLoading = {
                    binding.layoutProfileBody.pbLoading.isVisible = true
                    binding.layoutProfileBody.btnChangeProfile.isVisible = false
                }
            )
        }
    }


    private fun requestChangePassword() {
        viewModel.createChangePwdRequest()
        val dialog = AlertDialog.Builder(requireContext())
            .setMessage("Change password request sended to your email : ${viewModel.getCurrentUser()?.email} Please check to your inbox or spam")
            .setPositiveButton(
                "Okay"
            ) { dialog, id ->

            }.create()
        dialog.show()
    }



    private fun showUserData() {
        viewModel.getCurrentUser()?.let {
            binding.layoutProfileBody.nameEditText.setText(it.fullName)
//            binding.layoutProfileBody.usernameEditText.setText(it.username)
            binding.layoutProfileBody.emailEditText.setText(it.email)
        }
    }

    private fun checkNameValidation(): Boolean {
        val fullName = binding.layoutProfileBody.nameEditText.text.toString().trim()
        return if (fullName.isEmpty()) {
            binding.layoutProfileBody.nameInputLayout.isErrorEnabled = true
            binding.layoutProfileBody.nameInputLayout.error = getString(R.string.text_error_name_cannot_empty)
            false
        } else {
            binding.layoutProfileBody.nameInputLayout.isErrorEnabled = false
            true
        }
    }
    private fun observeEditMode() {
        viewModel.isEditMode.observe(viewLifecycleOwner) {
            binding.layoutProfileBody.nameEditText.isEnabled = it
            binding.layoutProfileBody.emailEditText.isEnabled = it
        }
        doEditProfile()
    }

    private fun setClickListener() {
        binding.layoutProfileHeader.ivIcEditText.setOnClickListener {
            viewModel.changeEditMode()
        }
        binding.layoutProfileBody.btnLogout.setOnClickListener {
            doLogout()
        }
        binding.layoutProfileBody.btnResetPassword.setOnClickListener {
            requestChangePassword()
        }
    }

    private fun navigateToMenu() {
        startActivity(Intent(requireContext(), MainActivity::class.java))
    }

    private fun doLogout() {
        val dialog = AlertDialog.Builder(requireContext()).setMessage("Do you want to logout ?")
            .setPositiveButton(
                "Yes"
            ) { dialog, id ->
                viewModel.doLogout()
                navigateToMenu()
            }
            .setNegativeButton(
                "No"
            ) { dialog, id ->
                //no-op , do nothing
            }.create()
        dialog.show()
    }
}