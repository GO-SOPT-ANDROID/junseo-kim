package org.android.go.sopt.presentation.main.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import org.android.go.sopt.R
import org.android.go.sopt.databinding.ActivityMainBinding
import org.android.go.sopt.presentation.gallery.view.HomeFragment
import org.android.go.sopt.presentation.home.view.GalleryFragment
import org.android.go.sopt.presentation.mypage.view.MyPageFragment
import org.android.go.sopt.presentation.search.view.SearchFragment

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        initFirstFragment(savedInstanceState)
        initBottomNavigationSelectListener()
        initBottomNavigationReselectListener()
    }

    private fun initBottomNavigationReselectListener() {
        binding.bnvMain.setOnItemReselectedListener {
            val currentFragment = supportFragmentManager.fragments.last()
            if (it.itemId == R.id.menu_home || currentFragment is GalleryFragment)
                (currentFragment as GalleryFragment).smoothScrollToTop()
        }
    }

    private fun initFirstFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                add(R.id.fcv_main, HomeFragment())
            }
        }
    }

    private fun initBottomNavigationSelectListener() {
        binding.bnvMain.setOnItemSelectedListener {
            changeFragment(
                when (it.itemId) {
                    R.id.menu_home -> HomeFragment()
                    R.id.menu_search -> SearchFragment()
                    R.id.menu_gallery -> GalleryFragment()
                    else -> MyPageFragment()
                }
            )
            true
        }
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            replace(R.id.fcv_main, fragment)
        }
    }
}