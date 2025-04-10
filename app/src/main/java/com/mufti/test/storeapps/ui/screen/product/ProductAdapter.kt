package com.mufti.test.storeapps.ui.screen.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mufti.test.storeapps.R
import com.mufti.test.storeapps.databinding.ItemRowProductBinding
import com.mufti.test.storeapps.domain.model.Product

class ProductAdapter : ListAdapter<Product, ProductAdapter.ListViewHolder>(diffCallback) {

    private var onProductSelected: (Product) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            ItemRowProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user, onProductSelected)
    }

    class ListViewHolder(private var binding: ItemRowProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            product: Product,
            onProductSelected: (Product) -> Unit
        ) {
            binding.apply {
                Glide.with(root)
                    .load(product.image)
                    .into(ivItemPhoto)

                tvItemPrice.text =
                    root.context.getString(R.string.label_price, product.price.toString())
                tvItemName.text = product.title
                tvItemRating.text = product.ratingRate

                root.setOnClickListener {
                    onProductSelected(product)
                }
            }
        }
    }

    fun setOnProductSelected(onProductSelected: (Product) -> Unit) {
        this.onProductSelected = onProductSelected
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem == newItem
            }
        }
    }
}
