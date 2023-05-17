package org.android.go.sopt.util.extensions

import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

fun Fragment.makeToastMessage(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}

fun Fragment.makeSnackbarMessage(rootView: View, message: String) {
    Snackbar.make(rootView, message, Snackbar.LENGTH_SHORT).show()
}