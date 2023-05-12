package org.android.go.sopt.presentation.signup

import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.android.go.sopt.R
import org.android.go.sopt.data.remote.ServicePool
import org.android.go.sopt.data.remote.model.RequestSignUpDto
import org.android.go.sopt.data.remote.model.ResponseSignUpDto
import org.android.go.sopt.databinding.ActivitySignUpBinding
import org.android.go.sopt.presentation.signin.view.SignInActivity
import org.android.go.sopt.util.PublicString.CONNECTION_FAIL
import org.android.go.sopt.util.PublicString.SERVER_COMMUNICATION_SUCCESS
import org.android.go.sopt.util.PublicString.UNEXPECTED_ERROR
import org.android.go.sopt.util.PublicString.USER_ID
import org.android.go.sopt.util.PublicString.USER_NAME
import org.android.go.sopt.util.PublicString.USER_PW
import org.android.go.sopt.util.PublicString.USER_SKILL
import org.android.go.sopt.util.extensions.hideKeyboard
import org.android.go.sopt.util.extensions.makeToastMessage
import retrofit2.Call
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {

    private val binding by lazy { ActivitySignUpBinding.inflate(layoutInflater) }
    private val signUpService by lazy { ServicePool.signUpService }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSignUpBtnClickListener()
    }

    private fun setSignUpBtnClickListener() {
        binding.btnSignUp.setOnClickListener {
            if (canUserSignUp()) {
                with(binding) {
                    signUpService.signUp(
                        RequestSignUpDto(
                            etSignUpId.text.toString(),
                            etSignUpPw.text.toString(),
                            etSignUpName.text.toString(),
                            etSignUpSkill.text.toString()
                        )
                    ).enqueue(object : retrofit2.Callback<ResponseSignUpDto> {
                        override fun onResponse(
                            call: Call<ResponseSignUpDto>,
                            response: Response<ResponseSignUpDto>,
                        ) {
                            // 응답이 왔다.
                            if (response.isSuccessful) {
                                // 찐 서버통신 성공
                                startActivity(
                                    Intent(
                                        this@SignUpActivity,
                                        SignInActivity::class.java
                                    )
                                )
                                Toast.makeText(
                                    this@SignUpActivity,
                                    response.body()?.message ?: SERVER_COMMUNICATION_SUCCESS,
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                // 서버통신 실패(40X)
                                Toast.makeText(
                                    this@SignUpActivity,
                                    response.body()?.message ?: UNEXPECTED_ERROR,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                        override fun onFailure(call: Call<ResponseSignUpDto>, t: Throwable) {
                            // 응답이 안 왔다.
                            Toast.makeText(
                                this@SignUpActivity,
                                CONNECTION_FAIL,
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    })
                }
            } else {
                Toast.makeText(
                    this,
                    getString(R.string.please_abide_by_the_membership_registration_conditions),
                    Toast.LENGTH_SHORT
                ).show()
            }

//            if (canUserSignIn()) {
//                completeSignUp()
//            } else {
//                makeToastMessage(getString(R.string.please_abide_by_the_membership_registration_conditions))
//            }
        }
    }

    private fun canUserSignUp(): Boolean {
        return binding.etSignUpId.text.length in 6..10 &&
                binding.etSignUpPw.text.length in 8..12 &&
                binding.etSignUpName.text.isNotBlank() &&
                binding.etSignUpSkill.text.isNotBlank()
    }

    private fun completeSignUp() {
        setResult(
            RESULT_OK, Intent(this, SignInActivity::class.java).putExtra(
                USER_ID, binding.etSignUpId.text.toString()
            ).putExtra(USER_PW, binding.etSignUpPw.text.toString())
                .putExtra(USER_NAME, binding.etSignUpName.text.toString())
                .putExtra(USER_SKILL, binding.etSignUpSkill.text.toString())
        )
        makeToastMessage(getString(R.string.you_have_successfully_registered_as_a_member))
        if (!isFinishing) finish()
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        hideKeyboard()
        return super.dispatchTouchEvent(ev)
    }
}