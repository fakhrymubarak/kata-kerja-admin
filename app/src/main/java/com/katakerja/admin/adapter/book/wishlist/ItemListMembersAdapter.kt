package com.katakerja.admin.adapter.book.wishlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.katakerja.admin.R
import com.katakerja.admin.core.domain.model.User
import com.katakerja.admin.databinding.ItemListMembersBinding
import com.katakerja.admin.helper.Base64

class ItemListMembersAdapter(private val dataSet: List<User>) :
    RecyclerView.Adapter<ItemListMembersAdapter.ViewHolder>() {
    var onItemClick: ((User) -> Unit)? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_list_members, viewGroup, false)
    )

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(dataSet[position])
    }

    override fun getItemCount() = dataSet.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemListMembersBinding.bind(view)
        fun bind(data: User) {
            binding.apply {
                try {
                    ivProfilePicture.load(Base64.decode(data.avatar))
                } catch (e: Exception) {
                    ivProfilePicture.load(R.drawable.img_cover_default)
                }

                tvName.text = data.name
                tvEmail.text = data.email
                tvRegDate.text = data.memberSince
            }
            itemView.setOnClickListener {
                onItemClick?.invoke(dataSet[layoutPosition])
            }
        }
    }
}
