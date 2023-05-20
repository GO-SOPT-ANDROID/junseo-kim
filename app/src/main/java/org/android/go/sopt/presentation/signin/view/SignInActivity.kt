package org.android.go.sopt.presentation.signin.view

import SharedPreferences
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import org.android.go.sopt.R
import org.android.go.sopt.data.remote.model.ResponseSignInDto.UserInfo
import org.android.go.sopt.databinding.ActivitySignInBinding
import org.android.go.sopt.presentation.main.view.MainActivity
import org.android.go.sopt.presentation.signin.viewmodel.SignInViewModel
import org.android.go.sopt.presentation.signup.view.SignUpActivity
import org.android.go.sopt.util.PublicString.USER_NAME
import org.android.go.sopt.util.PublicString.USER_SKILL
import org.android.go.sopt.util.extensions.hideKeyboard
import org.android.go.sopt.util.extensions.makeToastMessage

class SignInActivity : AppCompatActivity() {

    private val binding by lazy { ActivitySignInBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<SignInViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSignUpBtnClickEvent()
        setSignInBtnClickEvent()
        navigateToMainPageForSignedInUser()

        observeSignInResult()
        observeErrorResult()

        setContentView(binding.root)
    }

    private fun observeErrorResult() {
        viewModel.errorResult.observe(this) { errorMessage ->
            makeToastMessage(errorMessage)
        }
    }

    private fun observeSignInResult() {
        viewModel.signInResult.observe(this) { signInResult ->
            startActivity(
                Intent(
                    this@SignInActivity,
                    MainActivity::class.java
                )
            )
            setAutoSignIn()
            saveUserInfo(
                signInResult.data
            )
            makeToastMessage(
                signInResult.message
            )
        }
    }

    private fun setSignInBtnClickEvent() {
        binding.btnSignIn.setOnClickListener {
            viewModel.signIn(
                binding.etSignInId.text.toString(),
                binding.etSignInPw.text.toString()
            )
        }
    }

    private fun navigateToMainPageForSignedInUser() {
        if (SharedPreferences.getBoolean(getString(R.string.is_user_sign_in))) {
            startActivity(
                Intent(this, MainActivity::class.java).putExtra(
                    USER_NAME, SharedPreferences.getString(
                        USER_NAME
                    )
                ).putExtra(USER_SKILL, SharedPreferences.getString(USER_SKILL))
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            )
        }
    }

    private fun setAutoSignIn() {
        SharedPreferences.setBoolean(getString(R.string.is_user_sign_in), true)
    }

    private fun saveUserInfo(userInfo: UserInfo) {
        SharedPreferences.run {
            setString(USER_NAME, userInfo.name)
            setString(USER_SKILL, userInfo.skill)
        }
    }

    private fun setSignUpBtnClickEvent() {
        binding.btnSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        hideKeyboard()
        return super.dispatchTouchEvent(ev)
    }
}