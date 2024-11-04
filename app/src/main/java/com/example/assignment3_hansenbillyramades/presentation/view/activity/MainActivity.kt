package com.example.assignment3_hansenbillyramades.presentation.view.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.assignment3_hansenbillyramades.R
import com.example.assignment3_hansenbillyramades.data.source.local.PreferenceDataStore
import com.example.assignment3_hansenbillyramades.databinding.ActivityMainBinding
import com.example.assignment3_hansenbillyramades.presentation.view.fragment.ExploreFragment
import com.example.assignment3_hansenbillyramades.presentation.view.fragment.ItineraryFragment
import com.example.assignment3_hansenbillyramades.presentation.view.fragment.ProfileFragment
import com.google.android.material.navigation.NavigationBarView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var preferenceDataStore: PreferenceDataStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            val userDetails = preferenceDataStore.getUserDetails()
            val token = userDetails?.token

            if (token == null) {
                finish()
                return@launch
            }

            // Ambil selectedType dari intent
            //val selectedType = intent.getStringExtra(PreferencesTravelActivity.EXTRA_SELECTED_TYPE) ?: ""
            val selectedType = preferenceDataStore.getSelectedRecommendationType()

            binding.bottomNav.setOnItemSelectedListener(object :
                NavigationBarView.OnItemSelectedListener {
                override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
                    return when (menuItem.itemId) {
                        R.id.menu_explore -> {
                            replaceFragment(ExploreFragment(), token, selectedType)
                            true
                        }

                        R.id.menu_plan -> {
                            replaceFragment(ItineraryFragment(), token)
                            true
                        }

                        R.id.menu_profile -> {
                            replaceFragment(ProfileFragment(), token)
                            true
                        }

                        else -> false
                    }
                }
            })

            replaceFragment(ExploreFragment(), token, selectedType)
        }
    }

    private fun replaceFragment(fragment: Fragment, token: String, selectedType: String? = null) {
        val bundle = Bundle()
        bundle.putString("TOKEN", token)
        if (selectedType != null) {
            bundle.putString("SELECTED_TYPE", selectedType) // Menyimpan selectedType
        }
        fragment.arguments = bundle

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
