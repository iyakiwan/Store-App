package com.mufti.test.storeapps.ui.screen.product.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mufti.test.storeapps.R
import com.mufti.test.storeapps.databinding.FragmentAddCartBinding
import com.mufti.test.storeapps.domain.model.Product
import com.mufti.test.storeapps.utils.ViewModelFactory

class AddCartBottomSheetDialog : BottomSheetDialogFragment() {
    private var _binding: FragmentAddCartBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AddCartViewModel

    private var productId = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getArgument()
        setupViewModel()

        observerQuantityCart()

        getDetailProduct()
    }

    private fun getArgument() {
        val arguments = arguments ?: return
        productId = arguments.getString(BUNDLE_KEY_PRODUCT_ID).orEmpty()
    }

    private fun setupViewModel() {
        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireContext())

        viewModel = ViewModelProvider(
            this, factory
        )[AddCartViewModel::class.java]
    }

    private fun getDetailProduct() {
        if (productId.isNotEmpty()) {
            observeDetailProduct()
        } else {
            dismiss()
        }
    }

    private fun observeDetailProduct() {
        viewModel.detailProduct(this.productId.toInt()).observe(this) {
            setupDetailProduct(it)
        }
        viewModel.setCart(this.productId.toInt())

        binding.btnAddCart.setOnClickListener {
            viewModel.submitCart(productId.toInt())
            val message = if (viewModel.updateCart) {
                getString(R.string.updated_to_cart)
            } else {
                getString(R.string.added_to_cart)
            }
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            dismiss()
        }
    }

    private fun setupDetailProduct(product: Product) {
        binding.apply {
            Glide.with(requireContext())
                .load(product.image)
                .into(ivItemPhoto)

            tvItemPrice.text =
                getString(R.string.label_price, product.price)
            tvItemName.text = product.title

            ivItemAdd.setOnClickListener {
                viewModel.setQuantity(1)
            }

            ivItemRemove.setOnClickListener {
                viewModel.setQuantity(-1)
            }
        }
    }

    private fun observerQuantityCart() {
        viewModel.quantity.observe(viewLifecycleOwner) {
            binding.btnAddCart.text = if (viewModel.updateCart) {
                getString(R.string.update_to_cart)
            } else {
                getString(R.string.add_to_cart)
            }
            binding.chipItemQuantity.text = it.toString()
            binding.ivItemRemove.isEnabled = it > 1
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val BUNDLE_KEY_PRODUCT_ID = "product_id"

        @JvmStatic
        fun newInstance(
            productId: String,
        ): AddCartBottomSheetDialog {
            val fragment = AddCartBottomSheetDialog()
            val bundle = Bundle()
            bundle.putString(BUNDLE_KEY_PRODUCT_ID, productId)
            fragment.arguments = bundle
            return fragment
        }
    }
}
