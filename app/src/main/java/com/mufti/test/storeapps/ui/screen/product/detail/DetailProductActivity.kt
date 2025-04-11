package com.mufti.test.storeapps.ui.screen.product.detail

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.mufti.test.storeapps.R
import com.mufti.test.storeapps.databinding.ActivityDetailProductBinding
import com.mufti.test.storeapps.domain.model.Product
import com.mufti.test.storeapps.ui.screen.product.add.AddCartBottomSheetDialog
import com.mufti.test.storeapps.utils.ViewModelFactory
import com.mufti.test.storeapps.utils.extension.ActivityExtension.okAlertDialog

class DetailProductActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailProductBinding
    private lateinit var viewModel: DetailProductViewModel

    private var idProduct = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupWindow()
        setupViewModel()

        getArgument()
        setupView()

        getDetailProduct()
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
        )[DetailProductViewModel::class.java]
    }

    private fun getArgument() {
        if (intent?.extras != null) {
            idProduct = intent.getStringExtra(INTENT_ID_PRODUCT) ?: ""
        }
    }

    private fun setupView() {
        binding.apply {
            ivBack.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }

            ivAddCart.setOnClickListener {
                if (idProduct.isNotEmpty()) {
                    val dialog = AddCartBottomSheetDialog.newInstance(idProduct)
                    dialog.show(supportFragmentManager, dialog.tag)
                } else {
                    okAlertDialog(
                        title = getString(R.string.alert_title_product_not_found),
                        message = getString(R.string.alert_message_product_not_found),
                        positiveButtonText = getString(R.string.alert_button_ok)
                    )
                }
            }
        }
    }

    private fun getDetailProduct() {
        if (idProduct.isNotEmpty()) {
            observeDetailProduct()
        } else {
            okAlertDialog(
                title = getString(R.string.alert_title_product_not_found),
                message = getString(R.string.alert_message_product_not_found),
                positiveButtonText = getString(R.string.alert_button_ok)
            ) {
                finish()
            }
        }
    }

    private fun observeDetailProduct() {
        viewModel.detailProduct(this.idProduct.toInt()).observe(this) {
            binding.pgDetailProduct.isVisible = false
            binding.groupDetailProduct.isVisible = true
            setupDetailProduct(it)
        }
    }

    private fun setupDetailProduct(data: Product) {
        binding.apply {
            Glide.with(this@DetailProductActivity).load(data.image).into(ivDetailPhoto)
            tvDetailPrice.text = getString(R.string.label_price, data.price)
            tvDetailTitle.text = data.title
            tvDetailRating.text =
                getString(R.string.label_rating, data.ratingRate, data.ratingCount.toString())
            tvDetailCategory.text = data.category
            tvDetailDescription.text = data.description
        }
    }

    companion object {
        const val INTENT_ID_PRODUCT = "intent_id_product"
    }
}
