package org.android.go.sopt.presentation.signup.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import org.android.go.sopt.data.remote.ServicePool
import org.android.go.sopt.data.remote.model.RequestSignUpDto
import org.android.go.sopt.data.remote.model.ResponseSignUpDto
import org.android.go.sopt.util.PublicString.REGEX_ID
import org.android.go.sopt.util.PublicString.REGEX_PW
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern.compile


class SignUpViewModel : ViewModel() {

    val id: MutableLiveData<String> = MutableLiveData("")
    val isIdValid: LiveData<Boolean> = id.map { id ->
        checkIdIsValid(id)
    }

    private fun checkIdIsValid(id: String): Boolean {
        val idPattern = compile(REGEX_ID)
        return id.isBlank() || (id.length in 6..10 && idPattern.matcher(id).matches())
    }

    val pw: MutableLiveData<String> = MutableLiveData("")
    val isPwValid: LiveData<Boolean> = pw.map { pw ->
        checkPwIsValid(pw)
    }

    private fun checkPwIsValid(pw: String): Boolean {
        val pwPattern =
            compile(REGEX_PW)
        return pw.isBlank() || (pw.length in 6..10 && pwPattern.matcher(pw).matches())
    }


    private val _signUpResult: MutableLiveData<ResponseSignUpDto> = MutableLiveData()
    val signUpResult: LiveData<ResponseSignUpDto> = _signUpResult

    private val _errorResult: MutableLiveData<String> = MutableLiveData()
    val errorResult: LiveData<String> = _errorResult

    fun signUp(userData: RequestSignUpDto) {
        ServicePool.signUpService.signUp(userData).enqueue(object : Callback<ResponseSignUpDto> {

            override fun onResponse(
                call: Call<ResponseSignUpDto>,
                response: Response<ResponseSignUpDto>,
            ) {
                if (response.isSuccessful) {
                    _signUpResult.value = response.body()
                } else {
                    _errorResult.value = response.message()
                }
            }

            override fun onFailure(call: Call<ResponseSignUpDto>, t: Throwable) {
                _errorResult.value = t.toString()
            }

        })
    }
}