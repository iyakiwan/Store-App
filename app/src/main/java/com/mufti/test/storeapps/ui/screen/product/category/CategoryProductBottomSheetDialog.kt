package com.mufti.test.storeapps.ui.screen.product.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mufti.test.storeapps.R
import com.mufti.test.storeapps.databinding.FragmentCategoryBinding
import com.mufti.test.storeapps.utils.constant.CategoryProductType

class CategoryProductBottomSheetDialog : BottomSheetDialogFragment() {
    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!

    private var onCategoryProductSelected: (CategoryProductType) -> Unit = {}
    private var categoryProductType = CategoryProductType.ALL

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getArgument()
        setupView()
    }

    private fun getArgument() {
        val arguments = arguments ?: return
        val enumValueString = arguments.getString(BUNDLE_KEY_CATEGORY_TYPE)
        categoryProductType = CategoryProductType.valueOf(enumValueString!!)
    }

    private fun setupView() {
        binding.apply {
            val checklistDrawable =
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_check_circle_24)

            when (categoryProductType) {
                CategoryProductType.ALL -> {
                    btnCategoryAll.setCompoundDrawablesWithIntrinsicBounds(
                        checklistDrawable,
                        null,
                        null,
                        null
                    )
                }

                CategoryProductType.MEN_CLOTHING -> {
                    btnCategory1.setCompoundDrawablesWithIntrinsicBounds(
                        checklistDrawable,
                        null,
                        null,
                        null
                    )
                }

                CategoryProductType.JEWELERY -> {
                    btnCategory2.setCompoundDrawablesWithIntrinsicBounds(
                        checklistDrawable,
                        null,
                        null,
                        null
                    )
                }

                CategoryProductType.ELECTRONICS -> {
                    btnCategory3.setCompoundDrawablesWithIntrinsicBounds(
                        checklistDrawable,
                        null,
                        null,
                        null
                    )
                }

                CategoryProductType.WOMEN_CLOTHING -> {
                    btnCategory4.setCompoundDrawablesWithIntrinsicBounds(
                        checklistDrawable,
                        null,
                        null,
                        null
                    )
                }
            }

            btnCategory1.setOnClickListener {
                onCategoryProductSelected(CategoryProductType.MEN_CLOTHING)
                dismiss()
            }
            btnCategory2.setOnClickListener {
                onCategoryProductSelected(CategoryProductType.JEWELERY)
                dismiss()
            }
            btnCategory3.setOnClickListener {
                onCategoryProductSelected(CategoryProductType.ELECTRONICS)
                dismiss()
            }
            btnCategory4.setOnClickListener {
                onCategoryProductSelected(CategoryProductType.WOMEN_CLOTHING)
                dismiss()
            }
            btnCategoryAll.setOnClickListener {
                onCategoryProductSelected(CategoryProductType.ALL)
                dismiss()
            }
        }
    }

    fun setOnCategoryProductSelected(
        onCategoryProductSelected: (CategoryProductType) -> Unit
    ) {
        this.onCategoryProductSelected = onCategoryProductSelected
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val BUNDLE_KEY_CATEGORY_TYPE = "category_type"

        @JvmStatic
        fun newInstance(
            selectedCategoryProductType: CategoryProductType,
        ): CategoryProductBottomSheetDialog {
            val fragment = CategoryProductBottomSheetDialog()
            val bundle = Bundle()
            bundle.putString(BUNDLE_KEY_CATEGORY_TYPE, selectedCategoryProductType.name)
            fragment.arguments = bundle
            return fragment
        }
    }
}
