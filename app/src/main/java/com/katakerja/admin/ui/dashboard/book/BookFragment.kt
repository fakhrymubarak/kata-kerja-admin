package com.katakerja.admin.ui.dashboard.book

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.katakerja.admin.R
import com.katakerja.admin.adapter.book.cover.ItemBookCoverGridAdapter
import com.katakerja.admin.adapter.book.linear.ItemBookCoverLinearAdapter
import com.katakerja.admin.core.data.Resource
import com.katakerja.admin.core.domain.model.Book
import com.katakerja.admin.core.utils.viewBinding
import com.katakerja.admin.databinding.FragmentBookBinding
import com.katakerja.admin.ui.dashboard.book.add.AddBookActivity
import com.katakerja.admin.ui.dashboard.book.details.BookDetailsActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookFragment : Fragment(R.layout.fragment_book) {
    private val binding by viewBinding(FragmentBookBinding::bind)
    private val bookViewModel: BookViewModel by viewModels()

    private val searchMovieAdapter = ItemBookCoverGridAdapter()
    private var isSearchActive = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setSearchView()
        setItemLayout()

        val listCategory = arrayOf("Novel", "Self Improvement")

        setCategoryOne(listCategory[0])
        setCategoryTwo(listCategory[1])

        binding.btnAddBook.setOnClickListener {
            intentTo(AddBookActivity::class.java)
        }
    }

    private fun setItemLayout() {
        if (isSearchActive) {
            binding.itemSearchList.root.visibility = View.VISIBLE
            binding.itemExplore.root.visibility = View.INVISIBLE
        } else {
            binding.itemSearchList.root.visibility = View.INVISIBLE
            binding.itemExplore.root.visibility = View.VISIBLE
        }
    }

    private fun setSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                isSearchActive = true
                setItemLayout()
                searchMovieAdapter.setData(listOf())
                searchBook(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
    }

    private fun setCategoryOne(category: String) {
        binding.itemExplore.tvFirstCat.text = category
        bookViewModel.getBooksByCat(category).observe(viewLifecycleOwner, { listBookResource ->
            when (listBookResource) {
                is Resource.Loading -> {
                    setLoadingCatOne(true)
                }
                is Resource.Success -> {
                    listBookResource.data?.let { listBook ->
                        setLoadingCatOne(false)
                        if (listBook.isNotEmpty()) {
                            populateBookCategoryOne(listBook)
                        }
                    }
                }
                is Resource.Error -> {
                    setLoadingCatOne(false)
                    Toast.makeText(
                        requireContext(), listBookResource.message, Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }

    private fun setCategoryTwo(category: String) {
        binding.itemExplore.tvSecondCat.text = category
        bookViewModel.getBooksByCat(category).observe(viewLifecycleOwner, { listBookResource ->
            when (listBookResource) {
                is Resource.Loading -> {
                    setLoadingCatTwo(true)
                }
                is Resource.Success -> {
                    setLoadingCatTwo(false)
                    listBookResource.data?.let { listBook ->
                        if (listBook.isNotEmpty()) {
                            populateBookCategoryTwo(listBook)
                        }
                    }
                }
                is Resource.Error -> {
                    setLoadingCatTwo(false)
                    Toast.makeText(
                        requireContext(), listBookResource.message, Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }

    private fun searchBook(query: String) {
        bookViewModel.searchBooks(query).observe(viewLifecycleOwner, { listSearchedBookResource ->
            when (listSearchedBookResource) {
                is Resource.Loading -> {
                    setLoadingSearch(true)
                }
                is Resource.Success -> {
                    setLoadingSearch(false)
                    listSearchedBookResource.data?.let { listBook ->
                        if (listBook.isNotEmpty()) {
                            populateSearchedBooks(listBook)
                        }
                    }
                }
                is Resource.Error -> {
                    setLoadingSearch(false)
                    Toast.makeText(
                        requireContext(), listSearchedBookResource.message, Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }

    private fun populateSearchedBooks(listBook: List<Book>) {
        searchMovieAdapter.setData(listBook)
        binding.itemSearchList.rvSearch.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.itemSearchList.rvSearch.adapter = searchMovieAdapter
        searchMovieAdapter.onItemClick = { selectedData ->
            intentTo(BookDetailsActivity::class.java, selectedData.idBook)
        }
    }

    private fun populateBookCategoryOne(listData: List<Book>) {
        val adapter = ItemBookCoverLinearAdapter(listData.map { it })
        adapter.onItemClick = { selectedData ->
            intentTo(BookDetailsActivity::class.java, selectedData.idBook)
        }
        binding.itemExplore.rvFirstCat.adapter = adapter
    }

    private fun populateBookCategoryTwo(listData: List<Book>) {
        val adapter = ItemBookCoverGridAdapter()
        adapter.setData(listData)
        adapter.onItemClick = { selectedData ->
            intentTo(BookDetailsActivity::class.java, selectedData.idBook)
        }
        binding.itemExplore.rvSecondCat.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.itemExplore.rvSecondCat.adapter = adapter
    }

    private fun <T> intentTo(destination: Class<T>, idBook: Int? = null) {
        val intent = Intent(requireContext(), destination)
        if (idBook != null) intent.putExtra(BookDetailsActivity.EXTRA_ID_BOOK, idBook)
        startActivity(intent)
    }

    private fun setLoadingCatOne(state: Boolean) {
        binding.itemExplore.apply {
            if (state) {
                pbFirstCat.startShimmer()
                pbFirstCat.visibility = View.VISIBLE
                rvFirstCat.visibility = View.INVISIBLE
                tvFirstCat.visibility = View.INVISIBLE
            } else {
                pbFirstCat.stopShimmer()
                pbFirstCat.visibility = View.INVISIBLE
                rvFirstCat.visibility = View.VISIBLE
                tvFirstCat.visibility = View.VISIBLE
            }
        }
    }

    private fun setLoadingCatTwo(state: Boolean) {
        binding.itemExplore.apply {
            if (state) {
                pbSecondCat.startShimmer()
                pbSecondCat.visibility = View.VISIBLE
                rvSecondCat.visibility = View.INVISIBLE
                tvSecondCat.visibility = View.INVISIBLE
            } else {
                pbSecondCat.stopShimmer()
                pbSecondCat.visibility = View.INVISIBLE
                rvSecondCat.visibility = View.VISIBLE
                tvSecondCat.visibility = View.VISIBLE
            }
        }
    }

    private fun setLoadingSearch(state: Boolean) {
        binding.itemSearchList.apply {
            if (state) {
                pbSearchList.startShimmer()
                pbSearchList.visibility = View.VISIBLE
                rvSearch.visibility = View.INVISIBLE
            } else {
                pbSearchList.stopShimmer()
                pbSearchList.visibility = View.INVISIBLE
                rvSearch.visibility = View.VISIBLE
            }
        }
    }
}