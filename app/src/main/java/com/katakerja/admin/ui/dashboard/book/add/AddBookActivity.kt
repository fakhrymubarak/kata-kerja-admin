package com.katakerja.admin.ui.dashboard.book.add

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.katakerja.admin.R
import com.katakerja.admin.databinding.ActivityAddBookBinding
import com.katakerja.admin.helper.Base64
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddBookActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddBookBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBookBinding.inflate(layoutInflater)
        setContentView(binding.root)
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

}