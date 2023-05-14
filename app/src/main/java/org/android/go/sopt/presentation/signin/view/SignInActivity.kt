package org.android.go.sopt.presentation.signin.view

import SharedPreferences
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import org.android.go.sopt.R
import org.android.go.sopt.data.remote.ServicePool.signInService
import org.android.go.sopt.data.remote.model.RequestSignInDto
import org.android.go.sopt.data.remote.model.ResponseSignInDto
import org.android.go.sopt.data.remote.model.ResponseSignInDto.UserInfo
import org.android.go.sopt.databinding.ActivitySignInBinding
import org.android.go.sopt.presentation.main.view.MainActivity
import org.android.go.sopt.presentation.signup.SignUpActivity
import org.android.go.sopt.util.PublicString.CONNECTION_FAIL
import org.android.go.sopt.util.PublicString.SERVER_COMMUNICATION_SUCCESS
import org.android.go.sopt.util.PublicString.UNEXPECTED_ERROR
import org.android.go.sopt.util.PublicString.USER_NAME
import org.android.go.sopt.util.PublicString.USER_SKILL
import org.android.go.sopt.util.extensions.hideKeyboard
import org.android.go.sopt.util.extensions.makeToastMessage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignInActivity : AppCompatActivity() {

    private val binding by lazy { ActivitySignInBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSignUpBtnClickEvent()
        setSignInBtnClickEvent()
        navigateToMainPageForSignedInUser()

        setContentView(binding.root)
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

    private fun setSignInBtnClickEvent() {
        binding.btnSignIn.setOnClickListener {
            with(binding) {
                signInService.signIn(
                    RequestSignInDto(
                        etSignInId.text.toString(),
                        etSignInPw.text.toString()
                    )
                ).enqueue(
                    object : Callback<ResponseSignInDto> {
                        override fun onResponse(
                            call: Call<ResponseSignInDto>,
                            response: Response<ResponseSignInDto>,
                        ) {
                            if (response.isSuccessful) {
                                startActivity(
                                    Intent(
                                        this@SignInActivity,
                                        MainActivity::class.java
                                    )
                                )
                                setAutoSignIn()
                                response.body()?.data?.let { userInfo ->
                                    saveUserInfo(
                                        userInfo
                                    )
                                }
                                makeToastMessage(
                                    response.body()?.message
                                        ?: SERVER_COMMUNICATION_SUCCESS
                                )
                            } else {
                                makeToastMessage(response.body()?.message ?: UNEXPECTED_ERROR)
                            }
                        }

                        override fun onFailure(call: Call<ResponseSignInDto>, t: Throwable) {
                            makeToastMessage(CONNECTION_FAIL)
                        }

                    }
                )
            }
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