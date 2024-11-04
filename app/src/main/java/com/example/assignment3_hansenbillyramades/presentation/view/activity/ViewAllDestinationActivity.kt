package com.example.assignment3_hansenbillyramades.presentation.view.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment3_hansenbillyramades.R
import com.example.assignment3_hansenbillyramades.databinding.ActivityViewAllDestinationBinding
import com.example.assignment3_hansenbillyramades.domain.model.DestinationState
import com.example.assignment3_hansenbillyramades.presentation.adapter.ItemDestinationsAdapter
import com.example.assignment3_hansenbillyramades.presentation.listener.ListDestinationListener
import com.example.assignment3_hansenbillyramades.presentation.viewModel.ViewAllDestinationViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ViewAllDestinationActivity : AppCompatActivity(), ListDestinationListener {
    private lateinit var binding: ActivityViewAllDestinationBinding
    private lateinit var adapter: ItemDestinationsAdapter
    private val viewModel: ViewAllDestinationViewModel by viewModels()
    private lateinit var token: String
    private var currentPage = 1
    private var isLoading = false
    private var selectedType: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewAllDestinationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarViewAllDestination)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.baseline_chevron_left_32)
        supportActionBar?.title = "View All"

        lifecycleScope.launch {
            token = "Bearer ${viewModel.getToken() ?: return@launch}"

            binding.rvDestinationsAll.layoutManager =
                LinearLayoutManager(this@ViewAllDestinationActivity)
            adapter = ItemDestinationsAdapter(emptyList(), this@ViewAllDestinationActivity)
            binding.rvDestinationsAll.adapter = adapter

            viewModel.loadDestinations(currentPage, token, null, null)
            viewModel.getUserDetails()


            binding.rvDestinationsAll.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val layoutManager =
                        binding.rvDestinationsAll.layoutManager as LinearLayoutManager
                    val totalItemCount = layoutManager.itemCount
                    val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()

                    Log.d("Scroll coba", "OnScroll: $dy $totalItemCount $lastVisibleItemPosition")
                    when {
                        currentPage == 1 && dy >= 0 || dy > 0 && !isLoading && totalItemCount <= (lastVisibleItemPosition + 1) -> {
                            Log.d(
                                "Scroll coba",
                                "OnScroll: $dy $totalItemCount $lastVisibleItemPosition"
                            )
                            currentPage++
                            isLoading = true
                            viewModel.loadDestinations(currentPage, token, null, null)
                        }
                    }
                }
            })



            lifecycleScope.launch {
                viewModel.destinationState.collect(object : FlowCollector<DestinationState> {
                    override suspend fun emit(value: DestinationState) {
                        when (value) {
                            is DestinationState.Success -> {
                                Log.d(
                                    "ViewAllDestinationActivity",
                                    "Destinations received: ${value.destinations}"
                                )
                                adapter.addDestinations(value.destinations)
                                isLoading = false
                                Toast.makeText(
                                    this@ViewAllDestinationActivity,
                                    "Data loaded successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                            is DestinationState.Error -> {
                                Toast.makeText(
                                    this@ViewAllDestinationActivity,
                                    value.message,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                            is DestinationState.Loading -> {
                            }

                            else -> {}
                        }

                    }
                })
            }
        }
    }

    override fun onClick(id: Int) {
        val intent = Intent(this@ViewAllDestinationActivity, DetailDestinationActivity::class.java)
        intent.putExtra("id_destination", id)
        startActivity(intent)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}