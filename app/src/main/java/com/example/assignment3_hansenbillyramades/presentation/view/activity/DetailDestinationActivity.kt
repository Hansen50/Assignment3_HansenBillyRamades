package com.example.assignment3_hansenbillyramades.presentation.view.activity

import android.content.Intent
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.assignment3_hansenbillyramades.R
import com.example.assignment3_hansenbillyramades.databinding.ActivityDetailDestinationBinding
import com.example.assignment3_hansenbillyramades.domain.model.DestinationState
import com.example.assignment3_hansenbillyramades.domain.model.Destinations
import com.example.assignment3_hansenbillyramades.presentation.viewModel.DetailDestinationViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailDestinationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailDestinationBinding
    private val viewModel: DetailDestinationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDetailDestinationBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarDetail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.baseline_chevron_left_32)

        lifecycleScope.launch {
            val destinationId = intent.getIntExtra("id_destination", 0)
            val token = "Bearer ${viewModel.getToken()}"
            viewModel.getDestinationDetail(destinationId, token, "", "")
        }

        lifecycleScope.launch {
            viewModel.destinationState.collect(object : FlowCollector<DestinationState> {
                override suspend fun emit(value: DestinationState) {
                    when (value) {
                        is DestinationState.Loading -> {
                        }

                        is DestinationState.SuccessDetail -> {
                            val destination = value.destination
                            binding.tvDestinationName.text = destination.name
                            binding.chipTypeDetail.text = destination.type
                            binding.tvRating.text = destination.popularity
                            binding.tvContentDescription.text = destination.description
                            binding.tvCityName.text = destination.location
                            binding.tvActivites.text = destination.activity

                            supportActionBar?.title = destination.name

                            Glide.with(this@DetailDestinationActivity)
                                .load(value.destination.image)
                                .into(binding.ivDestinationPlaceholder)

                            val colorMatrix = ColorMatrix().apply {
                                setScale(0.8f, 0.8f, 0.8f, 1f)
                            }
                            binding.ivDestinationPlaceholder.colorFilter = ColorMatrixColorFilter(colorMatrix)

                            binding.btnAddItinetary.setOnClickListener {
                                val intent = Intent(this@DetailDestinationActivity, CreateItineraryActivity::class.java).apply {
                                    putExtra("destination", destination)
                                }
                                startActivity(intent)
                            }
                        }

                        is DestinationState.Error -> {
                            Toast.makeText(
                                this@DetailDestinationActivity,
                                "Error: ${value.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        else -> {}
                    }
                }
            })
        }
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
