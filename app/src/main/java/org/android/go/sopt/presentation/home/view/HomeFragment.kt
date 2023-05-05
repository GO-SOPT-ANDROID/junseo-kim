package org.android.go.sopt.presentation.home.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import org.android.go.sopt.R
import org.android.go.sopt.databinding.FragmentHomeBinding
import org.android.go.sopt.presentation.home.adapter.GoAndroidAdapter
import org.android.go.sopt.presentation.home.adapter.selection.GoAndroidItemDetailsLookup
import org.android.go.sopt.presentation.home.adapter.selection.SelectionItemKeyProvider
import org.android.go.sopt.presentation.home.viewmodel.HomeViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = requireNotNull(_binding) { "binding is null ...." }
    private val viewModel by viewModels<HomeViewModel>()

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

        initRecyclerView()
    }

    private fun initRecyclerView() {
        val adapter = GoAndroidAdapter()
        binding.rvHome.adapter = adapter
        adapter.submitList(viewModel.getPartMemberList())
        val tracker = buildTracker()
        adapter.setSelectionTracker(tracker)
    }

    private fun buildTracker(): SelectionTracker<Long> = SelectionTracker.Builder(
        getString(R.string.selected_member),
        binding.rvHome,
        SelectionItemKeyProvider(binding.rvHome),
        GoAndroidItemDetailsLookup(binding.rvHome),
        StorageStrategy.createLongStorage(),
    ).build()

    fun smoothScrollToTop() {
        binding.rvHome.smoothScrollToPosition(0)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}