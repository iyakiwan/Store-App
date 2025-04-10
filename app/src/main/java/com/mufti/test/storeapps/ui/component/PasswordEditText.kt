package com.mufti.test.storeapps.ui.component

import android.content.Context
import android.graphics.Canvas
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import com.google.android.material.textfield.TextInputEditText
import com.mufti.test.storeapps.R

class PasswordEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : TextInputEditText(context, attrs) {

    init {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().isEmpty()) error =
                    context.getString(R.string.alert_invalid_password)
            }

            override fun afterTextChanged(s: Editable?) {}

        })
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        hint = context.getString(R.string.input_hint_password)
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START

    }
}
