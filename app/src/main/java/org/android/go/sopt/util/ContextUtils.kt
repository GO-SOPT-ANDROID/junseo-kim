package org.android.go.sopt.util

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

fun Context.makeToastMessage(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.makeSnackbarMessage(rootView: View, message: String) {
    Snackbar.make(rootView, message, Snackbar.LENGTH_SHORT)
}