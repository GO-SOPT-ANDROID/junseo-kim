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
import java.util.Timer
import java.util.TimerTask

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding: FragmentSearchBinding
        get() = requireNotNull(_binding) { "binding is null ...." }
    private val kakaoSearchService by lazy { ServicePool.kakaoSearchService }
    private val adapter by lazy { KakaoSearchResultAdapter() }
    private var debounceTimer: Timer? = null

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

        initAdapter()
        setKakaoSearchViewQueryTextChangeEvent()
    }

    private fun setKakaoSearchViewQueryTextChangeEvent() {
        binding.svSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                debounceTimer?.cancel()

                debounceTimer = Timer()
                debounceTimer?.schedule(object : TimerTask() {
                    override fun run() {
                        if (query != null) {
                            getKakaoSearchResult(query)
                        }
                    }
                }, DEBOUNCE_DELAY)

                return true
            }

        })
    }

    private fun initAdapter() {
        binding.rvSearch.adapter = adapter
    }

    private fun getKakaoSearchResult(query: String) {
        kakaoSearchService.search(keyword = query).enqueue(
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
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        const val DEBOUNCE_DELAY: Long = 300
    }
}