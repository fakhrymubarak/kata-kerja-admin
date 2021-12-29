package com.katakerja.admin.adapter.book.transaction

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.katakerja.admin.R
import com.katakerja.admin.core.domain.model.BorrowedBook
import com.katakerja.admin.databinding.ItemBookTransactionBinding
import com.katakerja.admin.helper.Base64
import com.katakerja.admin.helper.DateFormat

class ItemBookTransactionAdapter(private val dataSet: List<BorrowedBook>) :
    RecyclerView.Adapter<ItemBookTransactionAdapter.ViewHolder>() {
    var onItemClick: ((BorrowedBook) -> Unit)? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_book_transaction, viewGroup, false)
        val binding = ItemBookTransactionBinding.bind(view)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(dataSet[position])
    }

    override fun getItemCount() = dataSet.size

    inner class ViewHolder(private val binding: ItemBookTransactionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: BorrowedBook) {
            binding.apply {
                try {
                    ivCover.load(Base64.decode(data.bookData.cover))
                } catch (e: Exception) {
                    ivCover.load(R.drawable.img_cover_default)
                }

                tvTitle.text = data.bookData.title
                tvAuthor.text = data.bookData.author
                tvPublisher.text = data.bookData.publisher
                tvYearReleased.text = data.bookData.releaseYear

                when (data.borrowStatus) {
                    1 -> {
                        tvDeadline.text = tvDeadline.context.getString(
                            R.string.turn_back_before,
                            DateFormat.fullDateToFormalDate(data.returnDate)
                        )
                    }
                    2 -> {
                        tvDeadline.setTextColor(tvDeadline.context.getColor(R.color.black))
                        tvDeadline.text = tvDeadline.context.getString(
                            R.string.read_date,
                            DateFormat.fullDateToFormalDate(data.returnDate)
                        )
                    }
                    3 -> {
                        tvDeadline.text = tvDeadline.context.getString(R.string.missing)
                    }
                }
            }
            itemView.setOnClickListener {
                onItemClick?.invoke(dataSet[layoutPosition])
            }
        }
    }
}
