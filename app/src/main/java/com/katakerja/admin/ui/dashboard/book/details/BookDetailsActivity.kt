package com.katakerja.admin.ui.dashboard.book.details

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.katakerja.admin.R
import com.katakerja.admin.core.data.Resource
import com.katakerja.admin.core.domain.model.Book
import com.katakerja.admin.databinding.ActivityBookDetailsBinding
import com.katakerja.admin.helper.Base64
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class BookDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBookDetailsBinding
    private lateinit var mBook: Book
    private val viewModel: BookDetailsViewModel by viewModels()

    private var isWishlist = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val idBook = intent.getIntExtra(EXTRA_ID_BOOK, 0)
        viewModel.getDetailBooks(idBook).observe(this, { bookResource ->
            if (bookResource != null) {
                when (bookResource) {
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        bookResource.data?.let { populateView(it) }
                    }
                    is Resource.Error -> {
                        Toast.makeText(this, bookResource.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    private fun populateView(book: Book) {
        mBook = book
        getWishlistStatus()
        binding.apply {
            tvDetailTitle.text = book.title
            tvDetailAuthor.text = book.author
            tvDetailCategory.text = book.category
            tvDetailYear.text = book.releaseYear
            tvDetailIsbn.text = book.isbn
            tvDetailPublisher.text = book.publisher
            tvDetailDesc.text = book.description

            try {
                ivDetailCover.load(Base64.decode(book.cover))
            } catch (e: Exception) {
                ivDetailCover.load(R.drawable.img_cover_default)
            }

            btnDetailBack.setOnClickListener {
                onBackPressed()
            }

            btnAddWishlist.setOnClickListener {
                binding.btnAddWishlist.isEnabled = false
                Timber.d("isWishlist $isWishlist")
                if (!isWishlist) {
                    insertWishlistBook()
                } else {
                    deleteWishlistBook()
                }
                isWishlist = !isWishlist
                setWishlistButton()
            }

            btnShare.setOnClickListener {
                shareBook(book)
            }
        }
    }

    private fun deleteWishlistBook() {
        viewModel.delWishBook().observe(this, { delResource ->
            binding.btnAddWishlist.isEnabled = true
            if (delResource != null) {
                when (delResource) {
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        Toast.makeText(this, delResource.message, Toast.LENGTH_SHORT).show()
                    }
                    is Resource.Error -> {
                        Toast.makeText(this, delResource.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    private fun insertWishlistBook() {
        viewModel.insertWishBook(mBook.idBook).observe(this, { insertResource ->
            binding.btnAddWishlist.isEnabled = true
            if (insertResource != null) {
                when (insertResource) {
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        Toast.makeText(this, insertResource.message, Toast.LENGTH_SHORT).show()
                    }
                    is Resource.Error -> {
                        Toast.makeText(this, insertResource.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    private fun getWishlistStatus() {
        binding.btnAddWishlist.isEnabled = true
        viewModel.getWishListStatus(mBook.idBook).observe(this, { status ->
            isWishlist = status
            setWishlistButton()
        })
    }

    private fun setWishlistButton() {
        if (isWishlist) {
            binding.btnAddWishlist.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_mark, 0, 0, 0
            )
        } else {
            binding.btnAddWishlist.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_add_dark, 0, 0, 0
            )
        }
    }

    private fun shareBook(book: Book) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(
                Intent.EXTRA_TEXT,
                "Book Title\t: ${book.title}" +
                        "\nAuthor\t: ${book.author} " +
                        "\nCategory\t: ${book.category} " +
                        "\nPublisher\t: ${book.publisher} " +
                        "\nRelease\t: ${book.releaseYear} " +
                        "\nDescription\t:\n" +
                        book.description
            )
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, book.title)
        startActivity(shareIntent)
    }

    companion object {
        const val EXTRA_ID_BOOK = "extra_id_book"
    }
}