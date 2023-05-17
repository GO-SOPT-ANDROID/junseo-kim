package org.android.go.sopt.presentation.gallery.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import org.android.go.sopt.R
import org.android.go.sopt.databinding.FragmentGalleryBinding
import org.android.go.sopt.presentation.gallery.adapter.GoAndroidAdapter
import org.android.go.sopt.presentation.gallery.adapter.selection.GoAndroidItemDetailsLookup
import org.android.go.sopt.presentation.gallery.adapter.selection.SelectionItemKeyProvider
import org.android.go.sopt.presentation.gallery.viewmodel.GalleryViewModel

class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null
    private val binding: FragmentGalleryBinding
        get() = requireNotNull(_binding) { "binding is null ...." }
    private val viewModel by viewModels<GalleryViewModel>()
    private var adapter: GoAndroidAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
    }

    private fun initRecyclerView() {
        adapter = GoAndroidAdapter()
        binding.rvGallery.adapter = adapter
        adapter?.submitList(viewModel.getPartMemberList())
        val tracker = buildTracker()
        adapter?.setSelectionTracker(tracker)
    }

    private fun buildTracker(): SelectionTracker<Long> = SelectionTracker.Builder(
        getString(R.string.selected_member),
        binding.rvGallery,
        SelectionItemKeyProvider(binding.rvGallery),
        GoAndroidItemDetailsLookup(binding.rvGallery),
        StorageStrategy.createLongStorage(),
    ).build()

    fun smoothScrollToTop() {
        binding.rvGallery.smoothScrollToPosition(0)
    }

    override fun onDestroyView() {
        _binding = null
        adapter = null
        super.onDestroyView()
    }
}