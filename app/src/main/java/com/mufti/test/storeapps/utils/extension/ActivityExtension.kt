package com.mufti.test.storeapps.utils.extension

import android.app.Activity
import android.content.DialogInterface
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

object ActivityExtension {
    fun Activity.okAlertDialog(
        title: String,
        message: String,
        positiveButtonText: String,
        onPositiveButtonPressed: () -> Unit = {},
    ) {
        AlertDialog.Builder(this).setTitle(title).setMessage(message).setCancelable(false)
            .setPositiveButton(positiveButtonText) { dialogInterface: DialogInterface, _: Int ->
                dialogInterface.dismiss()
                onPositiveButtonPressed()
            }.show()
    }

    @Suppress("LongParameterList")
    fun Activity.alertDialog(
        title: String,
        message: String,
        positiveButtonText: String,
        onPositiveButtonPressed: () -> Unit = {},
        negativeButtonText: String,
        onNegativeButtonPressed: () -> Unit = {}
    ) {
        AlertDialog.Builder(this).setTitle(title).setMessage(message).setCancelable(false)
            .setPositiveButton(positiveButtonText) { dialogInterface: DialogInterface, _: Int ->
                dialogInterface.dismiss()
                onPositiveButtonPressed()
            }.setNegativeButton(negativeButtonText) { dialogInterface: DialogInterface, _: Int ->
                dialogInterface.dismiss()
                onNegativeButtonPressed()
            }.show()
    }

    fun Activity.showToast(
        message: String
    ) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
