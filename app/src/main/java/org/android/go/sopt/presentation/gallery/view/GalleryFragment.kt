package org.android.go.sopt.presentation.gallery.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.load
import org.android.go.sopt.databinding.FragmentGalleryBinding
import org.android.go.sopt.presentation.gallery.adapter.GoAndroidAdapter
import org.android.go.sopt.presentation.gallery.viewmodel.GalleryViewModel
import org.android.go.sopt.util.ContentUriRequestBody

class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null
    private val binding: FragmentGalleryBinding
        get() = requireNotNull(_binding) { "binding is null ...." }
    private val viewModel by viewModels<GalleryViewModel>()
    private var adapter: GoAndroidAdapter? = null
    private val launcher =
        registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(maxItems = 3)) { uris ->
            with(binding) {
                when (uris.size) {
                    0 -> {
                        Toast.makeText(requireContext(), "이미지를 선택하지 않았습니다.", Toast.LENGTH_SHORT)
                            .show()
                    }

                    1 -> {
                        viewModel.setRequestBody(ContentUriRequestBody(requireContext(), uris[0]))
                        ivGalleryFirst.load(uris[0])
                        viewModel.uploadImage()
                    }

                    2 -> {
                        ivGalleryFirst.load(uris[0])
                        ivGallerySecond.load(uris[1])
                    }

                    3 -> {
                        ivGalleryFirst.load(uris[0])
                        ivGallerySecond.load(uris[1])
                        ivGalleryThird.load(uris[2])
                    }

                    else -> {
                        Toast.makeText(requireContext(), "3개까지의 이미지만 선택해주세요.", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    private val locatePermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                Toast.makeText(requireContext(), "허락받음", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "허락못받음", Toast.LENGTH_SHORT).show()
            }
        }

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
        locatePermissionLauncher.launch("android.permission.ACCESS_FINE_LOCATION")
        binding.btnGalleryPickImage.setOnClickListener {
            launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo))
        }
    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}