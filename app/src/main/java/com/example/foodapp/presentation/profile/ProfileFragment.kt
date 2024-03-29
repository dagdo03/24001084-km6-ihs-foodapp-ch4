package com.example.foodapp.presentation.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.load
import coil.transform.CircleCropTransformation
import com.example.foodapp.R
import com.example.foodapp.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding

    private val viewModel: ProfileViewModel by viewModels()

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
        setClickListener()
        observeEditMode()
        observeProfileData()
    }

    private fun observeProfileData() {
        viewModel.profileData.observe(viewLifecycleOwner) {
            binding.layoutProfileBody.ivProfilePicture.load(it.profileImg) {
                crossfade(true)
                error(R.drawable.ic_profile)
                transformations(CircleCropTransformation())
            }
            binding.layoutProfileBody.nameEditText.setText(it.name)
            binding.layoutProfileBody.usernameEditText.setText(it.username)
            binding.layoutProfileBody.emailEditText.setText(it.email)
        }
    }

    private fun observeEditMode() {
        viewModel.isEditMode.observe(viewLifecycleOwner) {
            binding.layoutProfileBody.emailEditText.isEnabled = it
            binding.layoutProfileBody.nameEditText.isEnabled = it
            binding.layoutProfileBody.emailEditText.isEnabled = it
        }
    }

    private fun setClickListener() {
        binding.layoutProfileHeader.ivIcEditText.setOnClickListener {
            viewModel.changeEditMode()
        }
    }
}