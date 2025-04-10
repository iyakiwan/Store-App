package com.mufti.test.storeapps.ui.screen.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mufti.test.storeapps.R
import com.mufti.test.storeapps.databinding.FragmentProfileBinding
import com.mufti.test.storeapps.ui.screen.auth.login.LoginActivity
import com.mufti.test.storeapps.utils.ViewModelFactory
import com.mufti.test.storeapps.utils.extension.FragmentExtension.alertDialog

class ProfileBottomSheetDialogFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding  = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        setupView()
    }

    private fun setupViewModel() {
        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireContext())

        viewModel = ViewModelProvider(
            this, factory
        )[ProfileViewModel::class.java]
    }

    private fun setupView() {
        viewModel.getUserName().observe(requireActivity()) {
            binding.tvUsername.text = it.userName
        }

        binding.btnLogout.setOnClickListener {
            alertDialog(
                title = getString(R.string.alert_title_logout),
                message = getString(R.string.alert_message_logout),
                positiveButtonText = getString(R.string.alert_button_yes),
                onPositiveButtonPressed = {
                    viewModel.logout()
                    launchLoginActivity()
                },
                negativeButtonText = getString(R.string.alert_button_no)
            )
        }
    }

    private fun launchLoginActivity() {
        val intent = Intent(requireContext(), LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
