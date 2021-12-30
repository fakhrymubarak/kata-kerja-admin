package com.katakerja.admin.adapter.book.wishlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.katakerja.admin.R
import com.katakerja.admin.core.domain.model.Book
import com.katakerja.admin.databinding.ItemBookWishlistBinding
import com.katakerja.admin.helper.Base64

class ItemBookWishlistAdapter(private val dataSet: List<Book>) :
    RecyclerView.Adapter<ItemBookWishlistAdapter.ViewHolder>() {
    var onItemClick: ((Book) -> Unit)? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_book_wishlist, viewGroup, false)
    )

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(dataSet[position])
    }

    override fun getItemCount() = dataSet.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemBookWishlistBinding.bind(view)
        fun bind(data: Book) {
            binding.apply {
                try {
                    ivCover.load(Base64.decode(data.cover))
                } catch (e: Exception) {
                    ivCover.load(R.drawable.img_cover_default)
                }

                tvTitle.text = data.title
                tvAuthor.text = data.author
                tvPublisher.text = data.publisher
                tvYearReleased.text = data.releaseYear
                tvStock.text = tvStock.context.getString(R.string.stock_ready, data.stock)
            }
            itemView.setOnClickListener {
                onItemClick?.invoke(dataSet[layoutPosition])
            }
        }
    }
}
