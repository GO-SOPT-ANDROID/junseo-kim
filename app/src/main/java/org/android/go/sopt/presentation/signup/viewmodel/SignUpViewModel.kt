package org.android.go.sopt.presentation.signup.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.android.go.sopt.data.remote.ServicePool
import org.android.go.sopt.data.remote.model.RequestSignUpDto
import org.android.go.sopt.data.remote.model.ResponseSignUpDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpViewModel : ViewModel() {
    private val _signUpResult: MutableLiveData<ResponseSignUpDto> = MutableLiveData()
    val signUpResult: LiveData<ResponseSignUpDto> = _signUpResult

    private val _errorResult: MutableLiveData<String> = MutableLiveData()
    val errorResult: LiveData<String> = _errorResult

    fun signUp(userData: RequestSignUpDto) {
        ServicePool.signUpService.signUp(userData).enqueue(
            object : Callback<ResponseSignUpDto> {
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

            }
        )
    }
}