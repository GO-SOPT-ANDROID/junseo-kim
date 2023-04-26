package org.android.go.sopt.presentation.signin.view

import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import org.android.go.sopt.MainActivity
import org.android.go.sopt.databinding.ActivitySignInBinding
import org.android.go.sopt.model.UserInfo
import org.android.go.sopt.presentation.signin.viewmodel.SignInViewModel
import org.android.go.sopt.presentation.signup.SignUpActivity
import org.android.go.sopt.util.IntentKey.USER_ID
import org.android.go.sopt.util.IntentKey.USER_NAME
import org.android.go.sopt.util.IntentKey.USER_PW
import org.android.go.sopt.util.IntentKey.USER_SKILL
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

        setContentView(binding.root)
    }

    private fun setSignIpBtnClickListener() {
        binding.btnSignIn.setOnClickListener {
            if (viewModel.isUserInfoCreated()) {
                if (viewModel.isUserInfoCorrect(
                        binding.etSignInId.text.toString(), binding.etSignInPw.text.toString()
                    )
                ) {
                    makeToastMessage("로그인에 성공하였습니다.")
                    navigateToMainPage()
                } else {
                    makeToastMessage("아이디 또는 비밀번호를 확인해주세요.")
                }
            } else {
                makeToastMessage("회원가입을 먼저 진행해주세요.")
            }
        }
    }

    private fun navigateToMainPage() {
        startActivity(
            Intent(this, MainActivity::class.java).putExtra(USER_NAME, viewModel.getUserName())
                .putExtra(USER_SKILL, viewModel.getUserSkill())
        )
        if (!isFinishing) finish()
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
                    makeToastMessage("회원가입에 실패하였습니다.")
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