package org.android.go.sopt.presentation.home.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.android.go.sopt.data.remote.ServicePool
import org.android.go.sopt.data.remote.ServicePool.reqresService
import org.android.go.sopt.data.remote.model.ResponseReqresDto
import org.android.go.sopt.databinding.FragmentHomeBinding
import org.android.go.sopt.presentation.home.view.adapter.ViewPagerAdapter
import org.android.go.sopt.util.PublicString.CONNECTION_FAIL
import org.android.go.sopt.util.PublicString.SERVER_COMMUNICATION_SUCCESS
import org.android.go.sopt.util.PublicString.UNEXPECTED_ERROR
import org.android.go.sopt.util.extensions.makeToastMessage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = requireNotNull(_binding) { "binding is null ...." }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getUser()
    }

    private fun getUser() {
        reqresService.getUsers(2).enqueue(object : Callback<ResponseReqresDto> {
            override fun onResponse(
                call: Call<ResponseReqresDto>,
                response: Response<ResponseReqresDto>,
            ) {
                if (response.isSuccessful) {
                    makeToastMessage(SERVER_COMMUNICATION_SUCCESS)
                    binding.pagerHome.adapter = ViewPagerAdapter(
                        response.body()?.data
                    )
                } else {
                    makeToastMessage(UNEXPECTED_ERROR)
                }
            }

            override fun onFailure(call: Call<ResponseReqresDto>, t: Throwable) {
                makeToastMessage(CONNECTION_FAIL)
            }

        })
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}