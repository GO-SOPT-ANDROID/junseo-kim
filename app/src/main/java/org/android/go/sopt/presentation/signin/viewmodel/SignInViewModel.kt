package org.android.go.sopt.presentation.signin.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.android.go.sopt.data.remote.ServicePool.signInService
import org.android.go.sopt.data.remote.model.RequestSignInDto
import org.android.go.sopt.data.remote.model.ResponseSignInDto
import org.android.go.sopt.util.PublicString.ID_IS_NULL
import org.android.go.sopt.util.PublicString.PW_IS_NULL
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignInViewModel : ViewModel() {
    val id: MutableLiveData<String> = MutableLiveData()
    val pw: MutableLiveData<String> = MutableLiveData()

    val canUserLogin: MediatorLiveData<Boolean> = MediatorLiveData()

    init {
        canUserLogin.apply {
            addSource(id) { canUserLogin.value = canUserLogin() }
            addSource(pw) { canUserLogin.value = canUserLogin() }
        }
    }

    private fun canUserLogin(): Boolean =
        id.value?.isNotBlank() == true && pw.value?.isNotBlank() == true

    private val _signInResult: MutableLiveData<ResponseSignInDto> = MutableLiveData()
    val signInResult: LiveData<ResponseSignInDto> = _signInResult

    private val _errorResult: MutableLiveData<String> = MutableLiveData()
    val errorResult: LiveData<String> = _errorResult

    fun signIn(view: View) {
        signInService.signIn(
            RequestSignInDto(
                id.value ?: throw NullPointerException(ID_IS_NULL),
                pw.value ?: throw java.lang.NullPointerException(PW_IS_NULL)
            )
        ).enqueue(
            object : Callback<ResponseSignInDto> {
                override fun onResponse(
                    call: Call<ResponseSignInDto>,
                    response: Response<ResponseSignInDto>,
                ) {
                    if (response.isSuccessful) {
                        _signInResult.value = response.body()
                    } else {
                        _errorResult.value = response.message()
                    }
                }

                override fun onFailure(call: Call<ResponseSignInDto>, t: Throwable) {
                    _errorResult.value = t.toString()
                }

            }
        )
    }
}