package org.android.go.sopt.presentation.signin.viewmodel

import androidx.lifecycle.ViewModel
import org.android.go.sopt.data.model.UserInfo

class SignInViewModel : ViewModel() {
    private lateinit var userInfo: UserInfo

    fun getUserInfo(_userInfo: UserInfo) {
        userInfo = _userInfo
    }

    fun isUserInfoCreated(): Boolean {
        return ::userInfo.isInitialized
    }

    fun isUserInfoCorrect(inputId: String, inputPW: String): Boolean {
        return userInfo.userId == inputId && userInfo.userPw == inputPW
    }

    fun getUserName(): String? {
        return userInfo.userName
    }

    fun getUserSkill(): String? {
        return userInfo.userSkill
    }
}