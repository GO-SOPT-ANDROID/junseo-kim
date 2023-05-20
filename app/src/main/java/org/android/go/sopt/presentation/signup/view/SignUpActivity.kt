package org.android.go.sopt.presentation.signup.view

import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import org.android.go.sopt.R
import org.android.go.sopt.data.remote.model.RequestSignUpDto
import org.android.go.sopt.databinding.ActivitySignUpBinding
import org.android.go.sopt.presentation.signin.view.SignInActivity
import org.android.go.sopt.presentation.signup.viewmodel.SignUpViewModel
import org.android.go.sopt.util.extensions.hideKeyboard
import org.android.go.sopt.util.extensions.makeToastMessage

class SignUpActivity : AppCompatActivity() {

    private val binding by lazy { ActivitySignUpBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<SignUpViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

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
        viewModel.errorResult.observe(this) { errorMessage ->
            makeToastMessage(
                errorMessage
            )
        }

    }

    private fun canUserSignUp(): Boolean {
        return binding.etSignUpId.text.length in 6..10 &&
                binding.etSignUpPw.text.length in 8..12 &&
                binding.etSignUpName.text.isNotBlank() &&
                binding.etSignUpSkill.text.isNotBlank()
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        hideKeyboard()
        return super.dispatchTouchEvent(ev)
    }
}