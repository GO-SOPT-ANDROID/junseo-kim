package org.android.go.sopt

import SharedPreferences
import android.app.Application

class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        initSharedPreferences()
    }

    private fun initSharedPreferences() {
        SharedPreferences.init(this)
    }
}