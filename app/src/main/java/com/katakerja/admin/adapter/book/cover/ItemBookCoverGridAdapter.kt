package com.katakerja.admin.adapter.book.cover

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.katakerja.admin.R
import com.katakerja.admin.core.domain.model.Book
import com.katakerja.admin.databinding.ItemBookCoverGridBinding
import com.katakerja.admin.helper.Base64

class ItemBookCoverGridAdapter : RecyclerView.Adapter<ItemBookCoverGridAdapter.ViewHolder>() {

    val listData = ArrayList<Book>()
    var onItemClick: ((Book) -> Unit)? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_book_cover_grid, viewGroup, false)
    )

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(listData[position])
    }

    override fun getItemCount() = listData.size

    fun setData(newListData: List<Book>) {
        if (newListData.isEmpty()) {
            listData.clear()
        } else {
            listData.clear()
            listData.addAll(newListData)
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemBookCoverGridBinding.bind(view)
        fun bind(data: Book) {
            binding.apply {
                try {
                    ivCover.load(Base64.decode(data.cover))
                } catch (e: Exception) {
                    ivCover.load(R.drawable.img_cover_default)
                }
            }
            itemView.setOnClickListener {
                onItemClick?.invoke(listData[layoutPosition])
            }
        }
    }
}
