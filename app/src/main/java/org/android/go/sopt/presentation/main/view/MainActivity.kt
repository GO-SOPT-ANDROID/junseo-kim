package org.android.go.sopt.presentation.main.view

import SharedPreferences
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.android.go.sopt.R
import org.android.go.sopt.databinding.ActivityMainBinding
import org.android.go.sopt.presentation.signin.view.SignInActivity
import org.android.go.sopt.util.PublicString.USER_NAME
import org.android.go.sopt.util.PublicString.USER_SKILL

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUserInfoOnScreen()
        setSignOutBtnClickListener()

        setContentView(binding.root)
    }

    private fun setSignOutBtnClickListener() {
        binding.btnMainSignOut.setOnClickListener {
            SharedPreferences.clear()
            startActivity(
                Intent(this, SignInActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            )

        }
    }

    private fun setUserInfoOnScreen() {
        if (SharedPreferences.getBoolean(getString(R.string.is_user_sign_in))) {
            setInfoForSignedInUser()
        } else {
            setInfoForFirstUser()
        }
    }

    private fun setInfoForFirstUser() {
        with(binding) {
            ivMainUser.setImageResource(R.drawable.img_user_profile)
            tvMainUserName.text =
                getString(R.string.user_name_is, intent.getStringExtra(USER_NAME))
            tvMainUserSkill.text = getString(
                R.string.user_skill_is, intent.getStringExtra(
                    USER_SKILL
                )
            )
        }
    }

    private fun setInfoForSignedInUser() {
        with(binding) {
            ivMainUser.setImageResource(R.drawable.img_user_profile)
            tvMainUserName.text =
                getString(R.string.user_name_is, SharedPreferences.getString(USER_NAME))
            tvMainUserSkill.text =
                getString(
                    R.string.user_skill_is, SharedPreferences.getString(USER_SKILL)
                )
        }
    }
}