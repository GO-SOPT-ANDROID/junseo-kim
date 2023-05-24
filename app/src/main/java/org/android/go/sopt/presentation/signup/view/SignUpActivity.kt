package org.android.go.sopt.presentation.signup.view

import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import org.android.go.sopt.R
import org.android.go.sopt.data.remote.model.RequestSignUpDto
import org.android.go.sopt.databinding.ActivitySignUpBinding
import org.android.go.sopt.presentation.signin.view.SignInActivity
import org.android.go.sopt.presentation.signup.viewmodel.SignUpViewModel
import org.android.go.sopt.util.extensions.hideKeyboard
import org.android.go.sopt.util.extensions.makeToastMessage

class SignUpActivity : AppCompatActivity() {

    lateinit var binding: ActivitySignUpBinding
    private val viewModel by viewModels<SignUpViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initDataBinding()
        setSignUpBtnClickEvent()
        observeSignUpResult()
        observeErrorResponse()

        observeSignUpInfo()
    }

    private fun observeSignUpInfo() {
        viewModel.isIdValid.observe(this) { isIdValid ->
            if (isIdValid)
                binding.layoutSignUpId.error = null
            else
                binding.layoutSignUpId.error = "조건 : 영문, 숫자가 포함된 6~10글자"
        }

        viewModel.isPwValid.observe(this) { isPwValid ->
            if (isPwValid)
                binding.layoutSignUpPw.error = null
            else
                binding.layoutSignUpPw.error = "조건 : 영문, 숫자, 특수문자가 포함된 6~12글자"
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

    private fun observeSignUpResult() {
        viewModel.signUpResult.observe(this) { signUpResult ->
            startActivity(
                Intent(
                    this@SignUpActivity,
                    SignInActivity::class.java
                )
            )
            makeToastMessage(
                signUpResult.message
            )
        }
    }

    private fun setSignUpBtnClickEvent() {
        with(binding) {
            btnSignUp.setOnClickListener {
                if (canUserSignUp()) {
                    viewModel.signUp(
                        RequestSignUpDto(
                            etSignUpId.text.toString(),
                            etSignUpPw.text.toString(),
                            etSignUpName.text.toString(),
                            etSignUpSkill.text.toString()
                        )
                    )
                } else {
                    makeToastMessage(getString(R.string.please_abide_by_the_membership_registration_conditions))
                }
            }
        }
    }

    private fun canUserSignUp(): Boolean {
        return (binding.etSignUpId.text?.length ?: 0) in (6..10) &&
                (binding.etSignUpPw.text?.length ?: 0) in (8..12) &&
                binding.etSignUpName.text.isNotBlank() &&
                binding.etSignUpSkill.text.isNotBlank()
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        hideKeyboard()
        return super.dispatchTouchEvent(ev)
    }
}