package org.android.go.sopt.presentation.main.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.android.go.sopt.R
import org.android.go.sopt.databinding.ActivityMainBinding
import org.android.go.sopt.util.PublicString.USER_NAME
import org.android.go.sopt.util.PublicString.USER_SKILL

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUserInfoOnScreen()

        setContentView(binding.root)
    }

    private fun setUserInfoOnScreen() {
        with(binding) {
            ivMainUser.setImageResource(R.drawable.img_user_profile)
            tvMainUserName.text = getString(R.string.user_name_is, intent.getStringExtra(USER_NAME))
            tvMainUserSkill.text = getString(
                R.string.user_skill_is, intent.getStringExtra(
                    USER_SKILL
                )
            )
        }
    }
}