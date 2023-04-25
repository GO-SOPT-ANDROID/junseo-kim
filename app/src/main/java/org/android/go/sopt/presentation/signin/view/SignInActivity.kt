package org.android.go.sopt.presentation.signin.view

import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import org.android.go.sopt.databinding.ActivitySignInBinding
import org.android.go.sopt.presentation.signup.SignUpActivity
import org.android.go.sopt.util.makeToastMessage

class SignInActivity : AppCompatActivity() {

    private val binding by lazy { ActivitySignInBinding.inflate(layoutInflater) }
    lateinit var signUpResultLauncher: ActivityResultLauncher<Intent>

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
                    makeToastMessage("회원가입 성공")
                } else {
                    makeToastMessage("회원가입 실패")
                }
            }
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val imm: InputMethodManager =
            getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        return super.dispatchTouchEvent(ev)
    }
}