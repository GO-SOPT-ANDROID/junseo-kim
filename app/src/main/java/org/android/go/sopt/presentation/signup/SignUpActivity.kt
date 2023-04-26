package org.android.go.sopt.presentation.signup

import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import org.android.go.sopt.databinding.ActivitySignUpBinding
import org.android.go.sopt.presentation.signin.view.SignInActivity
import org.android.go.sopt.util.IntentKey.USER_ID
import org.android.go.sopt.util.IntentKey.USER_NAME
import org.android.go.sopt.util.IntentKey.USER_PW
import org.android.go.sopt.util.IntentKey.USER_SKILL
import org.android.go.sopt.util.hideKeyboard
import org.android.go.sopt.util.makeToastMessage

class SignUpActivity : AppCompatActivity() {

    private val binding by lazy { ActivitySignUpBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSignUpBtnClickListener()
    }

    private fun setSignUpBtnClickListener() {
        binding.btnSignUp.setOnClickListener {
            completeSignUp()
        }
    }

    private fun completeSignUp() {
        setResult(
            RESULT_OK, Intent(this, SignInActivity::class.java).putExtra(
                USER_ID, binding.etSignUpId.text.toString()
            ).putExtra(USER_PW, binding.etSignUpPw.text.toString())
                .putExtra(USER_NAME, binding.etSignUpName.text.toString())
                .putExtra(USER_SKILL, binding.etSignUpSkill.text.toString())
        )
        makeToastMessage("회원가입에 성공하였습니다.")
        if (!isFinishing) finish()
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        hideKeyboard()
        return super.dispatchTouchEvent(ev)
    }
}