package com.katakerja.admin.ui.dashboard.wishlist

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.katakerja.admin.R
import com.katakerja.admin.adapter.book.wishlist.ItemListMembersAdapter
import com.katakerja.admin.core.data.Resource
import com.katakerja.admin.core.domain.model.User
import com.katakerja.admin.core.utils.viewBinding
import com.katakerja.admin.databinding.FragmentMembersBinding
import com.katakerja.admin.ui.dashboard.book.details.BookDetailsActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MembersFragment : Fragment(R.layout.fragment_members) {
    private val binding by viewBinding(FragmentMembersBinding::bind)
    private val viewModel: MembersViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getAuthToken().observe(viewLifecycleOwner, { authToken ->
            viewModel.getAllMembers(authToken).observe(viewLifecycleOwner, { listUserResource ->
                if (listUserResource != null) {
                    when (listUserResource) {
                        is Resource.Loading -> {
                            setListBookLoading(true)
                        }
                        is Resource.Success -> {
                            setListBookLoading(false)
                            listUserResource.data?.let { listUser -> populateWishlistUser(listUser.map { it }) }
                        }
                        is Resource.Error -> {
                            setListBookLoading(false)
                            Toast.makeText(
                                requireContext(), listUserResource.message, Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            })
        })
    }

    private fun populateWishlistUser(listData: List<User>) {
        if (listData.isEmpty()) {
            binding.animEmptyWishlist.visibility = View.VISIBLE
            binding.tvEmptyWishlist.visibility = View.VISIBLE
            binding.rvWishlist.visibility = View.GONE
        } else {
            binding.animEmptyWishlist.visibility = View.GONE
            binding.tvEmptyWishlist.visibility = View.GONE
            binding.rvWishlist.visibility = View.VISIBLE
            val adapter = ItemListMembersAdapter(listData)
            adapter.onItemClick = { selectedData ->
                Toast.makeText(requireContext(), selectedData.name, Toast.LENGTH_SHORT).show()
//                intentTo(BookDetailsActivity::class.java, selectedData.idUser)
            }
            binding.rvWishlist.adapter = adapter
        }
    }

    private fun <T> intentTo(destination: Class<T>, idBook: Int?) {
        val intent = Intent(requireContext(), destination)
        if (idBook != null) intent.putExtra(BookDetailsActivity.EXTRA_ID_BOOK, idBook)
        startActivity(intent)
    }

    private fun setListBookLoading(state: Boolean) {
        binding.apply {
            if (state) {
                pbWishList.startShimmer()
                pbWishList.visibility = View.VISIBLE
                rvWishlist.visibility = View.GONE
            } else {
                pbWishList.stopShimmer()
                pbWishList.visibility = View.GONE
                rvWishlist.visibility = View.VISIBLE
            }
        }
    }
}
