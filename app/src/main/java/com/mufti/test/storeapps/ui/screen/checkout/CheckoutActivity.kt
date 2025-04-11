package com.mufti.test.storeapps.ui.screen.checkout

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mufti.test.storeapps.R
import com.mufti.test.storeapps.databinding.ActivityCheckoutBinding
import com.mufti.test.storeapps.domain.model.Cart
import com.mufti.test.storeapps.utils.ViewModelFactory

class CheckoutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCheckoutBinding
    private lateinit var viewModel: CheckoutViewModel
    private val adapter by lazy { CheckoutAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupWindow()
        setupViewModel()

        setupView()

        observerListCheckout()
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
        )[CheckoutViewModel::class.java]
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
        binding.rvCheckout.setHasFixedSize(true)
        binding.rvCheckout.layoutManager = LinearLayoutManager(this)
        binding.rvCheckout.adapter = adapter
    }

    private fun observerListCheckout() {
        viewModel.getAllCart().observe(this) {
            adapter.submitList(it)
            totalComponent(it)
        }
    }

    private fun totalComponent(listCart: List<Cart>) {
        val totalItem = listCart.sumOf { it.quantity }
        val totalPrice = listCart.sumOf { it.product.price * it.quantity }

        binding.tvTotalItem.text = totalItem.toString()
        binding.tvTotalPrice.text = getString(R.string.label_price, totalPrice)
    }
}
