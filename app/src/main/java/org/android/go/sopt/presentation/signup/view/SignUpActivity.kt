package org.android.go.sopt.presentation.signup.view

import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import org.android.go.sopt.R
import org.android.go.sopt.presentation.signin.view.SignInActivity
import org.android.go.sopt.presentation.signup.viewmodel.SignUpViewModel
import org.android.go.sopt.util.extensions.hideKeyboard
import org.android.go.sopt.util.extensions.makeToastMessage

class SignUpActivity : AppCompatActivity() {

    lateinit var binding: org.android.go.sopt.databinding.ActivitySignUpBinding
    private val viewModel by viewModels<SignUpViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initDataBinding()
        observeSignUpResult()
        observeSignUpInfo()
    }

    private fun observeSignUpResult() {
        observeSuccessResult()
        observeErrorResponse()
    }

    private fun observeSignUpInfo() {
        viewModel.isIdValid.observe(this) { isIdValid ->
            if (isIdValid) binding.layoutSignUpId.error = null
            else binding.layoutSignUpId.error = getString(R.string.invalid_id_message)
        }

        viewModel.isPwValid.observe(this) { isPwValid ->
            if (isPwValid) binding.layoutSignUpPw.error = null
            else binding.layoutSignUpPw.error = getString(R.string.invalid_pw_message)
        }
    }

    private fun initDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
        binding.vm = viewModel
        binding.lifecycleOwner = this
    }

    private fun observeErrorResponse() {
        viewModel.errorResult.observe(this) { errorMessage ->
            makeToastMessage(
                errorMessage
            )
        }
    }

    private fun observeSuccessResult() {
        viewModel.signUpResult.observe(this) { signUpResult ->
            startActivity(
                Intent(
                    this@SignUpActivity, SignInActivity::class.java
                )
            )
            makeToastMessage(
                signUpResult.message
            )
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        hideKeyboard()
        return super.dispatchTouchEvent(ev)
    }
}