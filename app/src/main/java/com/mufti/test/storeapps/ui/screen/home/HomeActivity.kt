package com.mufti.test.storeapps.ui.screen.home

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.mufti.test.storeapps.data.Result
import com.mufti.test.storeapps.databinding.ActivityHomeBinding
import com.mufti.test.storeapps.ui.screen.product.ProductAdapter
import com.mufti.test.storeapps.ui.screen.product.detail.DetailProductActivity
import com.mufti.test.storeapps.ui.screen.product.detail.DetailProductActivity.Companion.INTENT_ID_PRODUCT
import com.mufti.test.storeapps.ui.screen.profile.ProfileBottomSheetDialogFragment
import com.mufti.test.storeapps.utils.ViewModelFactory

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var viewModel: HomeViewModel
    private val adapter by lazy { ProductAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupWindow()
        setupViewModel()

        setupView()

        observerListProduct()
        getProducts()
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
        )[HomeViewModel::class.java]
    }

    private fun setupView() {
        binding.apply {
            ivProfile.setOnClickListener {
                val bottomSheetDialogFragment = ProfileBottomSheetDialogFragment()
                bottomSheetDialogFragment.show(
                    supportFragmentManager,
                    bottomSheetDialogFragment.tag
                )
            }
        }

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.rvProduct.setHasFixedSize(true)
        binding.rvProduct.layoutManager = GridLayoutManager(this, 2)
        binding.rvProduct.adapter = adapter

        adapter.setOnProductSelected { product ->
            val intent = Intent(this, DetailProductActivity::class.java)
            intent.putExtra(INTENT_ID_PRODUCT, product.id.toString())
            startActivity(intent)
        }
    }

    private fun observerListProduct() {
        viewModel.listProduct.observe(this) {
            when (it) {
                is Result.Loading -> {
                    binding.pbProduct.isVisible = true
                }

                is Result.Success -> {
                    binding.pbProduct.isVisible = false
                    adapter.submitList(it.data)
                }

                is Result.Error -> {
                    binding.pbProduct.isVisible = false
                    Toast.makeText(this@HomeActivity, it.error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun getProducts() {
        viewModel.getListProduct()
    }
}
