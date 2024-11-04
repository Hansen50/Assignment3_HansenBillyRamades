package com.example.assignment3_hansenbillyramades.presentation.view.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment3_hansenbillyramades.databinding.FragmentExploreBinding
import com.example.assignment3_hansenbillyramades.domain.model.DestinationState
import com.example.assignment3_hansenbillyramades.domain.model.UserState
import com.example.assignment3_hansenbillyramades.presentation.adapter.ItemDestinationsAdapter
import com.example.assignment3_hansenbillyramades.presentation.listener.ListDestinationListener
import com.example.assignment3_hansenbillyramades.presentation.view.activity.DetailDestinationActivity
import com.example.assignment3_hansenbillyramades.presentation.view.activity.ViewAllDestinationActivity
import com.example.assignment3_hansenbillyramades.presentation.viewModel.ExploreViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ExploreFragment : Fragment(), ListDestinationListener {
    private var _binding: FragmentExploreBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ItemDestinationsAdapter
    private val viewModel: ExploreViewModel by viewModels()
    private lateinit var token: String
    private var isLoading = false
    private var currentPage = 1
    private var selectedType: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentExploreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            token = "Bearer ${viewModel.getToken()}"
            selectedType = arguments?.getString("SELECTED_TYPE") ?: ""
            selectedType = viewModel.getSelectedType() ?: ""
            Log.d("ExploreFragment", "Selected Type: $selectedType")
            if (selectedType.isNotEmpty()) {
                binding.tvYourRecommendation.text = selectedType
            }

            binding.rvDestinations.layoutManager = LinearLayoutManager(requireContext())
            adapter = ItemDestinationsAdapter(emptyList(), this@ExploreFragment)
            binding.rvDestinations.adapter = adapter

            viewModel.loadDestinations(currentPage, token, "", selectedType)
            viewModel.getUserDetails()

            binding.swipeRefreshLayout.setOnRefreshListener {
                currentPage = 1
                viewModel.loadDestinations(currentPage, token, "", selectedType)
                binding.swipeRefreshLayout.isRefreshing = false
            }

            binding.tvViewAll.setOnClickListener {
                val intent = Intent(requireContext(), ViewAllDestinationActivity::class.java)
                intent.putExtra("TOKEN", token)
                startActivity(intent)
            }

            binding.svSearchDstination.setOnQueryTextListener(object :
                SearchView.OnQueryTextListener {

                override fun onQueryTextSubmit(search: String?): Boolean {
                    if (!search.isNullOrEmpty()) {
                        currentPage = 1
                        viewModel.loadDestinations(currentPage, token, search, selectedType)
                    } else {
                        currentPage = 1
                        viewModel.loadDestinations(currentPage, token, "", selectedType)
                    }
                    binding.svSearchDstination.clearFocus()
                    return true
                }

                override fun onQueryTextChange(newSearch: String?): Boolean {
                    if (newSearch.isNullOrEmpty()) {
                        currentPage = 1
                        viewModel.loadDestinations(currentPage, token, "", selectedType)
                    }
                    return false
                }
            })

            binding.rvDestinations.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val layoutManager = binding.rvDestinations.layoutManager as LinearLayoutManager
                    val totalItemCount = layoutManager.itemCount
                    val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()

                    if (dy > 0 && !isLoading && totalItemCount <= (lastVisibleItemPosition + 1)) {
                        currentPage++
                        // true akan memanggil load data api
                        isLoading = true
                        viewModel.loadDestinations(currentPage, token, "", selectedType)
                    }
                }
            })

            lifecycleScope.launch {
                viewModel.destinationState.collect(object : FlowCollector<DestinationState> {
                    override suspend fun emit(value: DestinationState) {
                        when (value) {
                            is DestinationState.Success -> {
                                delay(2000L)
                                binding.shimmerLayout.stopShimmer()
                                binding.shimmerLayout.isVisible = false
                                binding.rvDestinations.isVisible = true
                                binding.swipeRefreshLayout.isRefreshing = false
                                adapter.addDestinations(value.destinations)

                                isLoading = false
                            }

                            is DestinationState.Error -> {
                                binding.shimmerLayout.startShimmer()
                                binding.shimmerLayout.isVisible = true
                                binding.rvDestinations.isVisible = false
                                binding.swipeRefreshLayout.isRefreshing = false
                                Toast.makeText(requireContext(), value.message, Toast.LENGTH_SHORT)
                                    .show()
                                isLoading = false
                            }

                            is DestinationState.Loading -> {
                                binding.shimmerLayout.startShimmer()
                                binding.shimmerLayout.isVisible = true
                                binding.rvDestinations.isVisible = false
                                binding.swipeRefreshLayout.isRefreshing = true
                            }

                            else -> {}
                        }
                    }
                })
            }

            lifecycleScope.launch {
                viewModel.userState.collect(object : FlowCollector<UserState> {
                    override suspend fun emit(value: UserState) {
                        when (value) {
                            is UserState.Success -> {
                                binding.tvNameUser.text = value.user?.firstName
                            }

                            is UserState.Error -> {
                            }

                            is UserState.Loading -> {
                            }

                            else -> {}
                        }
                    }
                })

            }

        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(id: Int) {
        val intent = Intent(requireContext(), DetailDestinationActivity::class.java)
        intent.putExtra("id_destination", id)
        startActivity(intent)
    }
}
