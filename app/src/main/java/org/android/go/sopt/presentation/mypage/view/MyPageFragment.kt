package org.android.go.sopt.presentation.mypage.view

import SharedPreferences
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.android.go.sopt.R
import org.android.go.sopt.databinding.FragmentMyPageBinding
import org.android.go.sopt.presentation.signin.view.SignInActivity
import org.android.go.sopt.util.PublicString

class MyPageFragment : Fragment() {

    private var _binding: FragmentMyPageBinding? = null
    private val binding: FragmentMyPageBinding
        get() = requireNotNull(_binding) { "binding is null ...." }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMyPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUserInfoOnScreen()
        setSignOutBtnClickListener()
    }

    private fun setSignOutBtnClickListener() {
        binding.btnMainSignOut.setOnClickListener {
            SharedPreferences.clear()
            startActivity(
                Intent(
                    requireContext(),
                    SignInActivity::class.java
                ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            )

        }
    }

    private fun setUserInfoOnScreen() {
        setInfoForSignedInUser()
    }

//    private fun setInfoForFirstUser() {
//        with(binding) {
//            ivMainUser.setImageResource(R.drawable.img_user_profile)
//            tvMainUserName.text =
//                getString(R.string.user_name_is, viewModel.getNameOfUser())
//            tvMainUserSkill.text = getString(
//                R.string.user_skill_is, viewModel.getSkillOfUser()
//            )
//        }
//    }

    private fun setInfoForSignedInUser() {
        with(binding) {
            ivMainUser.setImageResource(R.drawable.img_user_profile)
            tvMainUserName.text =
                getString(
                    R.string.user_name_is,
                    SharedPreferences.getString(PublicString.USER_NAME)
                )
            tvMainUserSkill.text =
                getString(
                    R.string.user_skill_is, SharedPreferences.getString(PublicString.USER_SKILL)
                )
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}