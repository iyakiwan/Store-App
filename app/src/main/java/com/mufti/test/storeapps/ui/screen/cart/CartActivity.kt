package com.mufti.test.storeapps.ui.screen.cart

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mufti.test.storeapps.R
import com.mufti.test.storeapps.databinding.ActivityCartBinding
import com.mufti.test.storeapps.domain.model.Cart
import com.mufti.test.storeapps.utils.ViewModelFactory

class CartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCartBinding
    private lateinit var viewModel: CartViewModel
    private val adapter by lazy { CartAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupWindow()
        setupViewModel()

        setupView()

        observerListCart()
    }

    private fun setupWindow() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setupViewModel() {
        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)

        viewModel = ViewModelProvider(
            this,
            factory
        )[CartViewModel::class.java]
    }

    private fun setupView() {
        binding.apply {
            ivBack.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.rvCart.setHasFixedSize(true)
        binding.rvCart.layoutManager = LinearLayoutManager(this)
        binding.rvCart.adapter = adapter

        adapter.setOnAddQuantityCard {
            viewModel.updateCartQuantity(
                cartId = it.id,
                quantity = it.quantity + 1
            )
        }

        adapter.setOnMinusQuantityCard {
            if (it.quantity == 1) {
                viewModel.deleteCart(it.id)
            } else {
                viewModel.updateCartQuantity(
                    cartId = it.id,
                    quantity = it.quantity - 1
                )
            }
        }
    }

    private fun observerListCart() {
        viewModel.getAllCart().observe(this) {
            adapter.submitList(it)
            totalPrice(it)
        }
    }

    private fun totalPrice(listCart: List<Cart>) {
        val totalPrice = listCart.sumOf { it.product.price * it.quantity }
        binding.tvTotalPrice.text = if (totalPrice > 0) {
            getString(R.string.label_price, totalPrice.toString())
        } else {
            "-"
        }
        binding.tvEmptyCart.isVisible = totalPrice == 0.0

        binding.btnCheckout.isEnabled = totalPrice > 0
    }
}
