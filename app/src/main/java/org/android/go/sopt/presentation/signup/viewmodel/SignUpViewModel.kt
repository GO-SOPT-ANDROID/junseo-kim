package org.android.go.sopt.presentation.signup.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import org.android.go.sopt.data.remote.ServicePool
import org.android.go.sopt.data.remote.model.RequestSignUpDto
import org.android.go.sopt.data.remote.model.ResponseSignUpDto
import org.android.go.sopt.util.PublicString.ID_IS_NULL
import org.android.go.sopt.util.PublicString.NAME_IS_NULL
import org.android.go.sopt.util.PublicString.PW_IS_NULL
import org.android.go.sopt.util.PublicString.REGEX_ID
import org.android.go.sopt.util.PublicString.REGEX_PW
import org.android.go.sopt.util.PublicString.SKILL_IS_NULL
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern.compile


class SignUpViewModel : ViewModel() {

    val id: MutableLiveData<String> = MutableLiveData()
    val isIdValid: LiveData<Boolean> = id.map { id ->
        checkIdIsValid(id)
    }

    private fun checkIdIsValid(id: String): Boolean {
        val idPattern = compile(REGEX_ID)
        return id.isBlank() || (id.length in 6..10 && idPattern.matcher(id).matches())
    }

    val pw: MutableLiveData<String> = MutableLiveData()
    val isPwValid: LiveData<Boolean> = pw.map { pw ->
        checkPwIsValid(pw)
    }

    private fun checkPwIsValid(pw: String): Boolean {
        val pwPattern = compile(REGEX_PW)
        return pw.isBlank() || (pw.length in 6..10 && pwPattern.matcher(pw).matches())
    }

    val name: MutableLiveData<String> = MutableLiveData()
    val skill: MutableLiveData<String> = MutableLiveData()

    val canUserSignUp: MediatorLiveData<Boolean> = MediatorLiveData<Boolean>()

    init {
        canUserSignUp.apply {
            addSource(id) { canUserSignUp.value = canUserSignUp() }
            addSource(pw) { canUserSignUp.value = canUserSignUp() }
            addSource(name) { canUserSignUp.value = canUserSignUp() }
            addSource(skill) { canUserSignUp.value = canUserSignUp() }
        }
    }

    private fun canUserSignUp(): Boolean {
        return isIdValid.value == true && isPwValid.value == true && !name.value.isNullOrBlank() && !skill.value.isNullOrBlank()
    }

    private val _signUpResult: MutableLiveData<ResponseSignUpDto> = MutableLiveData()
    val signUpResult: LiveData<ResponseSignUpDto> = _signUpResult

    private val _errorResult: MutableLiveData<String> = MutableLiveData()
    val errorResult: LiveData<String> = _errorResult

    fun signUp(view: View) {
        ServicePool.signUpService.signUp(
            RequestSignUpDto(
                id.value ?: throw NullPointerException(ID_IS_NULL),
                pw.value ?: throw NullPointerException(PW_IS_NULL),
                name.value ?: throw NullPointerException(NAME_IS_NULL),
                skill.value ?: throw NullPointerException(SKILL_IS_NULL)
            )
        ).enqueue(object : Callback<ResponseSignUpDto> {
            override fun onResponse(
                call: Call<ResponseSignUpDto>,
                response: Response<ResponseSignUpDto>,
            ) {
                if (response.isSuccessful) {
                    _signUpResult.value = response.body()
                } else {
                    _errorResult.value = response.body()?.message.toString()
                }
            }

            override fun onFailure(call: Call<ResponseSignUpDto>, t: Throwable) {
                _errorResult.value = t.message.toString()
            }
        })
    }
}