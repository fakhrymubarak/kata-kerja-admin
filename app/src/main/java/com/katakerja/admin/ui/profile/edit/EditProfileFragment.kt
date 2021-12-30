package com.katakerja.admin.ui.profile.edit

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.load
import coil.transform.CircleCropTransformation
import com.katakerja.admin.R
import com.katakerja.admin.core.data.Resource
import com.katakerja.admin.core.domain.model.User
import com.katakerja.admin.core.utils.viewBinding
import com.katakerja.admin.databinding.FragmentEditProfileBinding
import com.katakerja.admin.helper.Base64
import com.katakerja.admin.helper.Converter.transformIntoDatePicker
import com.katakerja.admin.helper.FormValidator.editTextIsNotEmpty
import com.katakerja.admin.helper.FormValidator.emailFormatMatched
import com.katakerja.admin.ui.profile.ProfileViewModel
import com.katakerja.admin.ui.profile.profile.ProfileFragmentArgs
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class EditProfileFragment : Fragment(R.layout.fragment_edit_profile) {
    private val binding by viewBinding(FragmentEditProfileBinding::bind)
    private val viewModel: ProfileViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle: Bundle = requireActivity().intent?.extras!!
        val user = ProfileFragmentArgs.fromBundle(bundle).extraUser
        populateUer(user)

        binding.apply {
            etBornDate.transformIntoDatePicker(requireContext(), "yyyy-MM-dd", Date())
            btnUpdateProfile.setOnClickListener {
                if (tilName.editTextIsNotEmpty() &&
                    tilEmail.editTextIsNotEmpty() && tilEmail.emailFormatMatched() &&
                    tilBornDate.editTextIsNotEmpty() &&
                    tilPhone.editTextIsNotEmpty()
                ) {
                    val name = etName.text.toString()
                    val email = etEmail.text.toString()
                    val bornDate = etBornDate.text.toString()
                    val phoneNumber = etPhone.text.toString()

                    vmUpdateProfile(name, email, bornDate, phoneNumber)
                }
            }
        }
    }

    private fun populateUer(user: User) {
        populateAvatar(user.avatar)
        binding.apply {
            etName.setText(user.name)
            etAddress.setText(user.fullAddress)
            etBornDate.setText(user.bornDate)
            etEmail.setText(user.email)
            etPhone.setText(user.phoneNumber)
        }
    }

    private fun vmUpdateProfile(
        name: String, email: String, bornDate: String, phoneNumber: String
    ) {
        viewModel.updateProfile(email, name, bornDate, phoneNumber)
            .observe(viewLifecycleOwner, { userResource ->
                if (userResource != null) {
                    when (userResource) {
                        is Resource.Loading -> {
                            setLoading(true)
                        }
                        is Resource.Success -> {
                            setLoading(false)
                            userResource.data?.let { populateUer(it) }
                            Toast.makeText(
                                requireContext(),
                                getString(R.string.success_update),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        is Resource.Error -> {
                            Toast.makeText(
                                requireContext(),
                                userResource.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            })
    }

    private fun populateAvatar(avatar: String?) {
        var isAvatarAdded: Boolean
        binding.apply {
            isAvatarAdded = if (avatar != null) {
                try {
                    ivEditAvatar.load(Base64.decode(avatar)) {
                        transformations(
                            CircleCropTransformation()
                        )
                    }
                    btnUpdateAvatar.setImageResource(R.drawable.ic_delete)
                    true
                } catch (e: Exception) {
                    ivEditAvatar.load(R.drawable.ic_empty_avatar)
                    false
                }
            } else {
                ivEditAvatar.load(R.drawable.ic_empty_avatar)
                btnUpdateAvatar.setImageResource(R.drawable.ic_add)
                false
            }

            btnUpdateAvatar.setOnClickListener {
                isAvatarAdded = if (isAvatarAdded) {
                    ivEditAvatar.load(R.drawable.ic_empty_avatar)
                    btnUpdateAvatar.setImageResource(R.drawable.ic_add)
                    false
                } else {
                    // Add Picture
                    btnUpdateAvatar.setImageResource(R.drawable.ic_delete)
                    true
                }
            }
        }
    }

    private fun setLoading(state: Boolean) {
        enableBtnLogin(state)
        binding.pbUpdate.visibility = if (state) View.VISIBLE else View.GONE
    }

    private fun enableBtnLogin(state: Boolean) {
        binding.apply {
            btnUpdateProfile.isEnabled = !state
            if (state) {
                btnUpdateProfile.background = AppCompatResources.getDrawable(
                    requireContext(),
                    R.drawable.shape_rec_fill_gray_light
                )
            } else {
                btnUpdateProfile.background = AppCompatResources.getDrawable(
                    requireContext(),
                    R.drawable.shape_rec_fill_blue
                )
            }
        }
    }
}