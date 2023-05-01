package org.android.go.sopt.presentation.signin.view

import SharedPreferences
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import org.android.go.sopt.R
import org.android.go.sopt.data.model.UserInfo
import org.android.go.sopt.databinding.ActivitySignInBinding
import org.android.go.sopt.presentation.main.view.MainActivity
import org.android.go.sopt.presentation.signin.viewmodel.SignInViewModel
import org.android.go.sopt.presentation.signup.SignUpActivity
import org.android.go.sopt.util.PublicString.USER_ID
import org.android.go.sopt.util.PublicString.USER_NAME
import org.android.go.sopt.util.PublicString.USER_PW
import org.android.go.sopt.util.PublicString.USER_SKILL
import org.android.go.sopt.util.extensions.hideKeyboard
import org.android.go.sopt.util.extensions.makeToastMessage

class SignInActivity : AppCompatActivity() {

    private val binding by lazy { ActivitySignInBinding.inflate(layoutInflater) }
    private lateinit var signUpResultLauncher: ActivityResultLauncher<Intent>
    private val viewModel by viewModels<SignInViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSignUpResultLauncher()
        setSignUpBtnClickListener()
        setSignIpBtnClickListener()
        navigateToMainPageForSignedInUser()

        setContentView(binding.root)
    }

    private fun navigateToMainPageForSignedInUser() {
        if (SharedPreferences.getBoolean(getString(R.string.is_user_sign_in))) navigateToMainPageByAutoSignIn()
    }

    private fun navigateToMainPageByAutoSignIn() {
        startActivity(
            Intent(this, MainActivity::class.java).putExtra(
                USER_NAME, SharedPreferences.getString(
                    USER_NAME
                )
            ).putExtra(USER_SKILL, SharedPreferences.getString(USER_SKILL))
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        )
    }

    private fun setSignIpBtnClickListener() {
        binding.btnSignIn.setOnClickListener {
            if (!viewModel.isUserInfoCreated()) {
                makeToastMessage(getString(R.string.please_register_as_a_member_first))
                return@setOnClickListener
            }

            if (viewModel.isUserInfoCorrect(
                    binding.etSignInId.text.toString(), binding.etSignInPw.text.toString()
                )
            ) {
                makeToastMessage(getString(R.string.login_was_successful))
                navigateToMainPage()
                setAutoSignIn()
            } else makeToastMessage(getString(R.string.please_check_your_id_or_password))

        }
    }

    private fun setAutoSignIn() {
        SharedPreferences.run {
            setBoolean(getString(R.string.is_user_sign_in), true)
            setString(USER_NAME, viewModel.getUserName())
            setString(USER_SKILL, viewModel.getUserSkill())
        }
    }

    private fun navigateToMainPage() {
        startActivity(
            Intent(this, MainActivity::class.java).putExtra(USER_NAME, viewModel.getUserName())
                .putExtra(USER_SKILL, viewModel.getUserSkill())
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        )
    }

    private fun setSignUpBtnClickListener() {
        binding.btnSignUp.setOnClickListener {
            signUpResultLauncher.launch(Intent(this, SignUpActivity::class.java))
        }
    }

    private fun setSignUpResultLauncher() {
        signUpResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    saveUserInfo(result.data)
                } else {
                    makeToastMessage(getString(R.string.member_registration_failed))
                }
            }
    }

    private fun saveUserInfo(info: Intent?) {
        info?.run {
            viewModel.getUserInfo(
                UserInfo(
                    userId = getStringExtra(USER_ID),
                    userPw = getStringExtra(USER_PW),
                    userName = getStringExtra(USER_NAME),
                    userSkill = getStringExtra(USER_SKILL)
                )
            )
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        hideKeyboard()
        return super.dispatchTouchEvent(ev)
    }
}