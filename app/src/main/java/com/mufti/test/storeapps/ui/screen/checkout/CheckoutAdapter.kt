package com.mufti.test.storeapps.ui.screen.checkout

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mufti.test.storeapps.R
import com.mufti.test.storeapps.databinding.ItemRowCheckoutBinding
import com.mufti.test.storeapps.domain.model.Cart

class CheckoutAdapter : ListAdapter<Cart, CheckoutAdapter.ListViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            ItemRowCheckoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val cart = getItem(position)
        holder.bind(cart)
    }

    class ListViewHolder(private var binding: ItemRowCheckoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            cart: Cart
        ) {
            binding.apply {
                Glide.with(root)
                    .load(cart.product.image)
                    .into(ivItemPhoto)

                tvItemPrice.text =
                    root.context.getString(
                        R.string.label_checkout,
                        cart.product.price,
                        cart.quantity.toString()
                    )
                tvItemName.text = cart.product.title
            }
        }
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Cart>() {
            override fun areItemsTheSame(oldItem: Cart, newItem: Cart): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Cart, newItem: Cart): Boolean {
                return oldItem == newItem
            }
        }
    }
}
