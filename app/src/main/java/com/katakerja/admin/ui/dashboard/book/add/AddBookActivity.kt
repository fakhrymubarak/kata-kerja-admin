package com.katakerja.admin.ui.dashboard.book.add

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import coil.load
import com.github.dhaval2404.imagepicker.ImagePicker
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.katakerja.admin.R
import com.katakerja.admin.core.data.Resource
import com.katakerja.admin.databinding.ActivityAddBookBinding
import com.katakerja.admin.helper.Base64
import com.katakerja.admin.helper.FormValidator.editTextIsNotEmpty
import com.katakerja.admin.ui.dashboard.book.BookViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddBookActivity : AppCompatActivity(), PermissionListener {
    private lateinit var binding: ActivityAddBookBinding
    private val viewModel: BookViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBookBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btnAddBack.setOnClickListener { onBackPressed() }
            btnUpdateCover.setOnClickListener { takePicture() }
            btnAddBook.setOnClickListener {
                if (tilIsbn.editTextIsNotEmpty() && tilAuthor.editTextIsNotEmpty() &&
                    tilPublisher.editTextIsNotEmpty() && tilReleaseYear.editTextIsNotEmpty() &&
                    tilCategory.editTextIsNotEmpty() && tilStock.editTextIsNotEmpty() &&
                    tilDescription.editTextIsNotEmpty() && tilTitle.editTextIsNotEmpty()
                ) {
                    val isbn = etIsbn.text.toString()
                    val title = etTitle.text.toString()
                    val author = etAuthor.text.toString()
                    val imgCover =
                        "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"
                    val publisher = etPublisher.text.toString()
                    val releaseYear = etReleaseYear.text.toString().toInt()
                    val category = etCategory.text.toString()
                    val stock = etStock.text.toString().toInt()
                    val desc = etDescription.text.toString()

                    vmAddNewBook(
                        isbn, title, author, imgCover, publisher,
                        releaseYear, category, stock, desc
                    )
                }
            }
        }
    }

    private fun takePicture() {
        ImagePicker.with(this)
            .crop()
            .compress(512)
            .start()
    }

    private fun vmAddNewBook(
        isbn: String, title: String, author: String, imgCover: String, publisher: String,
        releaseYear: Int, category: String, stock: Int, desc: String
    ) {
        viewModel.addBook(
            isbn = isbn,
            title = title,
            author = author,
            imgCover = imgCover,
            releaseYear = releaseYear,
            publisher = publisher,
            category = category,
            stock = stock,
            description = desc,
        ).observe(this, { addBookResource ->
            if (addBookResource != null) {
                when (addBookResource) {
                    is Resource.Loading -> {
                        setLoading(true)
                    }
                    is Resource.Success -> {
                        setLoading(false)
                        Toast.makeText(this, addBookResource.message, Toast.LENGTH_SHORT).show()
                    }
                    is Resource.Error -> {
                        setLoading(false)
                        Toast.makeText(this, addBookResource.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    private fun populateCoverBook(avatar: String?) {
        var isAvatarAdded: Boolean
        binding.apply {
            isAvatarAdded = if (avatar != null) {
                try {
                    ivCoverAdd.load(Base64.decode(avatar))
                    btnUpdateCover.setImageResource(R.drawable.ic_delete)
                    true
                } catch (e: Exception) {
                    ivCoverAdd.load(R.drawable.img_cover_default)
                    false
                }
            } else {
                ivCoverAdd.load(R.drawable.img_cover_default)
                btnUpdateCover.setImageResource(R.drawable.ic_add)
                false
            }

            btnUpdateCover.setOnClickListener {
                isAvatarAdded = if (isAvatarAdded) {
                    ivCoverAdd.load(R.drawable.img_cover_default)
                    btnUpdateCover.setImageResource(R.drawable.ic_add)
                    false
                } else {
                    // Add Picture
                    btnUpdateCover.setImageResource(R.drawable.ic_delete)
                    true
                }
            }
        }
    }

    private fun setLoading(state: Boolean) {
        enableBtnAddBook(state)
        binding.pbUpdate.visibility = if (state) View.VISIBLE else View.GONE
    }

    private fun enableBtnAddBook(state: Boolean) {
        binding.apply {
            btnAddBook.isEnabled = !state
            if (state) {
                btnAddBook.background = AppCompatResources.getDrawable(
                    this@AddBookActivity,
                    R.drawable.shape_rec_fill_gray_light
                )
            } else {
                btnAddBook.background = AppCompatResources.getDrawable(
                    this@AddBookActivity,
                    R.drawable.shape_rec_fill_blue
                )
            }
        }
    }

    override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
        takePicture()
    }

    override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
        Toast.makeText(
            this,
            getString(R.string.failed_access_camera),
            Toast.LENGTH_LONG
        ).show()
    }

    override fun onPermissionRationaleShouldBeShown(p0: PermissionRequest?, p1: PermissionToken?) {}
}