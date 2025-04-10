package com.mufti.test.storeapps.utils.extension

import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment

object FragmentExtension {

    fun Fragment.alertDialog(
        title: String,
        message: String,
        positiveButtonText: String,
        onPositiveButtonPressed: () -> Unit = {},
        negativeButtonText: String,
        onNegativeButtonPressed: () -> Unit = {}
    ) {
        AlertDialog.Builder(requireContext()).setTitle(title).setMessage(message).setCancelable(false)
            .setPositiveButton(positiveButtonText) { dialogInterface: DialogInterface, _: Int ->
                dialogInterface.dismiss()
                onPositiveButtonPressed()
            }.setNegativeButton(negativeButtonText) { dialogInterface: DialogInterface, _: Int ->
                dialogInterface.dismiss()
                onNegativeButtonPressed()
            }.show()
    }
}