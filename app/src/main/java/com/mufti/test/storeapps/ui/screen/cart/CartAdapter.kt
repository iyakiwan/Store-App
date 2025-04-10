package com.mufti.test.storeapps.ui.screen.cart

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mufti.test.storeapps.R
import com.mufti.test.storeapps.databinding.ItemRowCartBinding
import com.mufti.test.storeapps.domain.model.Cart

class CartAdapter : ListAdapter<Cart, CartAdapter.ListViewHolder>(diffCallback) {
    private var onAddQuantityCard: (Cart) -> Unit = {}
    private var onMinusQuantityCard: (Cart) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            ItemRowCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user, onAddQuantityCard, onMinusQuantityCard)
    }

    class ListViewHolder(private var binding: ItemRowCartBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private fun getDrawableCompat(context: Context, drawableResId: Int): Drawable? {
            return ContextCompat.getDrawable(context, drawableResId)
        }

        fun bind(
            cart: Cart,
            onAddQuantityCard: (Cart) -> Unit,
            onRemoveQuantityCard: (Cart) -> Unit,
        ) {
            binding.apply {
                val drawableMinus = if (cart.quantity == 1) {
                    getDrawableCompat(root.context, R.drawable.ic_delete_24)
                } else {
                    getDrawableCompat(root.context, R.drawable.ic_remove_24)
                }

                Glide.with(root)
                    .load(cart.product.image)
                    .into(ivItemPhoto)

                tvItemPrice.text =
                    root.context.getString(R.string.label_price, cart.product.price.toString())
                tvItemName.text = cart.product.title

                chipItemQuantity.text = cart.quantity.toString()

                ivItemRemove.setImageDrawable(drawableMinus)

                ivItemAdd.setOnClickListener {
                    onAddQuantityCard(cart)
                }

                ivItemRemove.setOnClickListener {
                    onRemoveQuantityCard(cart)
                }
            }
        }
    }

    fun setOnAddQuantityCard(onAddQuantityCard: (Cart) -> Unit) {
        this.onAddQuantityCard = onAddQuantityCard
    }

    fun setOnMinusQuantityCard(onMinusQuantityCard: (Cart) -> Unit) {
        this.onMinusQuantityCard = onMinusQuantityCard
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
