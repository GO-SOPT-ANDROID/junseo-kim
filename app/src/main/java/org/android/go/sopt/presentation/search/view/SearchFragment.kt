package org.android.go.sopt.presentation.search.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import org.android.go.sopt.data.remote.ServicePool
import org.android.go.sopt.data.remote.model.ResponseKakaoSearchDto
import org.android.go.sopt.databinding.FragmentSearchBinding
import org.android.go.sopt.presentation.search.adapter.KakaoSearchResultAdapter
import org.android.go.sopt.util.PublicString.CONNECTION_FAIL
import org.android.go.sopt.util.PublicString.UNEXPECTED_ERROR
import org.android.go.sopt.util.extensions.makeToastMessage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding: FragmentSearchBinding
        get() = requireNotNull(_binding) { "binding is null ...." }
    private val kakaoSearchService by lazy { ServicePool.kakaoSearchService }
    private val adapter by lazy { KakaoSearchResultAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvSearch.adapter = adapter

        binding.svSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                kakaoSearchService.search(keyword = binding.svSearch.query.toString()).enqueue(
                    object : Callback<ResponseKakaoSearchDto> {
                        override fun onResponse(
                            call: Call<ResponseKakaoSearchDto>,
                            response: Response<ResponseKakaoSearchDto>,
                        ) {
                            if (response.isSuccessful) {
                                adapter.submitList(
                                    response.body()?.documents
                                        ?: listOf<ResponseKakaoSearchDto.Document>()
                                )
                            } else {
                                makeToastMessage(UNEXPECTED_ERROR)
                            }
                        }

                        override fun onFailure(call: Call<ResponseKakaoSearchDto>, t: Throwable) {
                            makeToastMessage(CONNECTION_FAIL)
                        }

                    }
                )
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            }

        })
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}