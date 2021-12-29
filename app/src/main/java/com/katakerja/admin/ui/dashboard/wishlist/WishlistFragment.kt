package com.katakerja.admin.ui.dashboard.wishlist

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.katakerja.admin.R
import com.katakerja.admin.adapter.book.wishlist.ItemBookWishlistAdapter
import com.katakerja.admin.core.data.Resource
import com.katakerja.admin.core.domain.model.Book
import com.katakerja.admin.core.utils.viewBinding
import com.katakerja.admin.databinding.FragmentWishlistBinding
import com.katakerja.admin.ui.dashboard.book.details.BookDetailsActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WishlistFragment : Fragment(R.layout.fragment_wishlist) {
    private val binding by viewBinding(FragmentWishlistBinding::bind)
    private val viewModel: WishListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getUserId().observe(this, { userId ->
            viewModel.getWishlistBooks(userId).observe(viewLifecycleOwner, { listBookResource ->
                if (listBookResource != null) {
                    when (listBookResource) {
                        is Resource.Loading -> {
                            setListBookLoading(true)
                        }
                        is Resource.Success -> {
                            setListBookLoading(false)
                            listBookResource.data?.let { listBook -> populateWishlistBook(listBook.map { it.bookData }) }
                        }
                        is Resource.Error -> {
                            setListBookLoading(false)
                            Toast.makeText(
                                requireContext(), listBookResource.message, Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            })
        })
    }

    private fun populateWishlistBook(listData: List<Book>) {
        if (listData.isEmpty()) {
            binding.animEmptyWishlist.visibility = View.VISIBLE
            binding.tvEmptyWishlist.visibility = View.VISIBLE
            binding.rvWishlist.visibility = View.GONE
        } else {
            binding.animEmptyWishlist.visibility = View.GONE
            binding.tvEmptyWishlist.visibility = View.GONE
            binding.rvWishlist.visibility = View.VISIBLE
            val adapter = ItemBookWishlistAdapter(listData)
            adapter.onItemClick = { selectedData ->
                intentTo(BookDetailsActivity::class.java, selectedData.idBook)
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
