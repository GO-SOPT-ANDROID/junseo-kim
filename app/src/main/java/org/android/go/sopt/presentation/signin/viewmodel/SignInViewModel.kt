package org.android.go.sopt.presentation.signin.viewmodel

import androidx.lifecycle.ViewModel
import org.android.go.sopt.model.UserInfo

class SignInViewModel : ViewModel() {
    private lateinit var userInfo: UserInfo

    fun getUserInfo(_userInfo: UserInfo) {
        userInfo = _userInfo
    }

    fun isUserInfoCreated(): Boolean {
        return ::userInfo.isInitialized
    }

    fun isUserInfoCorrect(input: UserInfo): Boolean {
        return userInfo == input
    }

}