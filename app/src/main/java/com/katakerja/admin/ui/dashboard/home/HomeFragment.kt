package com.katakerja.admin.ui.dashboard.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.katakerja.admin.R
import com.katakerja.admin.adapter.book.transaction.ItemBookTransactionAdapter
import com.katakerja.admin.core.data.Resource
import com.katakerja.admin.core.domain.model.BorrowedBook
import com.katakerja.admin.core.utils.viewBinding
import com.katakerja.admin.databinding.FragmentHomeBinding
import com.katakerja.admin.ui.dashboard.book.details.BookDetailsActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    private val binding by viewBinding(FragmentHomeBinding::bind)
    private val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getUserId().observe(this, { userId ->
            observeBookTransaction(userId)
        })
    }

    private fun observeBookTransaction(userId: Int) {
        viewModel.getBorrowedBooksById(userId).observe(viewLifecycleOwner, { listTransRes ->
            if (listTransRes != null) {
                when (listTransRes) {
                    is Resource.Loading -> {
                        setListBookLoading(true)
                    }
                    is Resource.Success -> {
                        setListBookLoading(false)
                        listTransRes.data?.let { listTransaction ->
                            if (listTransaction.isNotEmpty()) {
                                val listBorrowed = listTransaction.filter { it.borrowStatus == 1 }
                                val listHasRead = listTransaction.filter { it.borrowStatus == 2 }
                                populateBorrowedBook(listBorrowed)
                                populateHasReadBook(listHasRead)
                                binding.animEmptyBorrow.visibility = View.GONE
                                binding.tvEmptyBorrow.visibility = View.GONE
                            } else {
                                binding.animEmptyBorrow.visibility = View.VISIBLE
                                binding.tvEmptyBorrow.visibility = View.VISIBLE
                                binding.layoutStatistics.tvNumberRead.text = "0"
                                binding.layoutStatistics.tvNumberBorrow.text = "0"
                            }
                        }
                    }
                    is Resource.Error -> {
                        setListBookLoading(false)
                        Toast.makeText(
                            requireContext(), listTransRes.message, Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        })
    }

    private fun populateBorrowedBook(listData: List<BorrowedBook>) {
        if (listData.isEmpty()) {
            binding.tvBorrowed.visibility = View.GONE
            binding.rvBorrowed.visibility = View.GONE
            binding.layoutStatistics.tvNumberBorrow.text = "0"
        } else {
            binding.tvBorrowed.visibility = View.VISIBLE
            binding.rvBorrowed.visibility = View.VISIBLE

            val itemBookHomeAdapter = ItemBookTransactionAdapter(listData)
            itemBookHomeAdapter.onItemClick = { selectedData ->
                intentTo(BookDetailsActivity::class.java, selectedData.bookData.idBook)
            }
            binding.rvBorrowed.adapter = itemBookHomeAdapter
            binding.layoutStatistics.tvNumberBorrow.text = listData.size.toString()
        }
    }

    private fun populateHasReadBook(listData: List<BorrowedBook>) {
        if (listData.isEmpty()) {
            binding.tvHasRead.visibility = View.GONE
            binding.rvHasRead.visibility = View.GONE
            binding.layoutStatistics.tvNumberRead.text = "0"
        } else {
            binding.tvHasRead.visibility = View.VISIBLE
            binding.rvHasRead.visibility = View.VISIBLE
            val itemBookHomeAdapter = ItemBookTransactionAdapter(listData)
            itemBookHomeAdapter.onItemClick = { selectedData ->
                intentTo(BookDetailsActivity::class.java, selectedData.bookData.idBook)
            }
            binding.rvHasRead.adapter = itemBookHomeAdapter
            binding.layoutStatistics.tvNumberRead.text = listData.size.toString()
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
                pbTopRated.startShimmer()
                pbTopRated.visibility = View.VISIBLE
                rvBorrowed.visibility = View.GONE
                tvHasRead.visibility = View.GONE
                rvHasRead.visibility = View.GONE
            } else {
                pbTopRated.stopShimmer()
                pbTopRated.visibility = View.GONE
                rvBorrowed.visibility = View.VISIBLE
                tvHasRead.visibility = View.VISIBLE
                rvHasRead.visibility = View.VISIBLE
            }
        }
    }
}