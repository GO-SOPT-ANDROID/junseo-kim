package org.android.go.sopt.presentation.signin.view

import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import org.android.go.sopt.databinding.ActivitySignInBinding
import org.android.go.sopt.model.UserInfo
import org.android.go.sopt.presentation.signin.viewmodel.SignInViewModel
import org.android.go.sopt.presentation.signup.SignUpActivity
import org.android.go.sopt.util.IntentKey.USER_ID
import org.android.go.sopt.util.IntentKey.USER_NAME
import org.android.go.sopt.util.IntentKey.USER_PW
import org.android.go.sopt.util.IntentKey.USER_SKILL
import org.android.go.sopt.util.makeToastMessage

class SignInActivity : AppCompatActivity() {

    private val binding by lazy { ActivitySignInBinding.inflate(layoutInflater) }
    lateinit var signUpResultLauncher: ActivityResultLauncher<Intent>
    private val viewModel by viewModels<SignInViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSignUpResultLauncher()
        setSignUpBtnClickListener()

        setContentView(binding.root)
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
        val imm: InputMethodManager =
            getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        return super.dispatchTouchEvent(ev)
    }
}