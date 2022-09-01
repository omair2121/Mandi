package com.secrets.formers.utils

import android.content.Intent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView

fun Spinner.onDropDownSelected(fn: () -> Unit) {
    this.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(
            parent: AdapterView<*>?,
            view: View?,
            position: Int,
            id: Long
        ) {
            fn.invoke()
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {

        }
    }
}

fun EditText.onDoneClick(fn: (TextView) -> Unit) {
    this.setOnEditorActionListener { v, actionId, event ->
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            fn.invoke(v)
            v.error = null
            true
        } else false
    }
}

fun Intent.getFromExtra(key: String) = this.extras?.getString(key) ?: ""
