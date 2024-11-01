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
import com.example.assignment3_hansenbillyramades.data.source.local.ItineraryEntity
import com.example.assignment3_hansenbillyramades.data.source.local.TravelMateDatabase
import com.example.assignment3_hansenbillyramades.databinding.ActivityCreateItineraryBinding
import com.example.assignment3_hansenbillyramades.domain.model.DestinationState
import com.example.assignment3_hansenbillyramades.domain.model.Destinations
import com.example.assignment3_hansenbillyramades.presentation.viewModel.DetailDestinationViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CreateItineraryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateItineraryBinding
    private val viewModel: DetailDestinationViewModel by viewModels()
    private lateinit var db: TravelMateDatabase
    private var isEditMode: Boolean = false
    private lateinit var currentItinerary: ItineraryEntity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateItineraryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = TravelMateDatabase.getDatabase(this@CreateItineraryActivity)

        setSupportActionBar(binding.toolbarCreateItinerary)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.baseline_chevron_left_32)
        supportActionBar?.title = "Create Itinerary"

        val destination = intent.getParcelableExtra<Destinations>("destination")
        isEditMode = intent.getBooleanExtra("isEdit", false)

        // Jika dalam mode edit, ambil data itinerary yang ada
        if (isEditMode) {
            currentItinerary = intent.getParcelableExtra("itinerary")!!
            setupEditMode(currentItinerary)
        } else {
            if (destination != null) {
                setupDestinationView(destination)
            } else {
                Toast.makeText(this, "Data tidak tersedia", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnCreateItinerary.setOnClickListener {
            val notes = binding.etNotes.text.toString().trim()

            if (notes.isNotEmpty()) {
                lifecycleScope.launch {
                    if (isEditMode) {
                        currentItinerary.note = notes
                        db.itineraryDao().updateItinerary(currentItinerary)
                        Toast.makeText(this@CreateItineraryActivity, "Itinerary updated!", Toast.LENGTH_SHORT).show()
                    } else {
                        val itinerary = ItineraryEntity(
                            destinationId = destination?.id ?: 0,
                            destinationName = destination?.name ?: "",
                            cityName = destination?.location ?: "",
                            description = destination?.description ?: "",
                            rating = destination?.popularity ?: "",
                            type = destination?.type ?: "",
                            activities = destination?.type ?: "",
                            note = notes,
                            image = destination?.image ?: ""
                        )
                        db.itineraryDao().addItinerary(itinerary)
                        Toast.makeText(this@CreateItineraryActivity, "Itinerary saved!", Toast.LENGTH_SHORT).show()
                    }

                    val intent = Intent(this@CreateItineraryActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            } else {
                Toast.makeText(this, "Notes cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupEditMode(itinerary: ItineraryEntity) {
        binding.tvDestinationName.text = itinerary.destinationName
        binding.tvCityName.text = itinerary.cityName
        binding.tvContentDescription.text = itinerary.description
        binding.tvRating.text = itinerary.rating
        binding.chipTypeDetail.text = itinerary.type
        binding.tvActivites.text = itinerary.activities
        binding.etNotes.setText(itinerary.note)

        supportActionBar?.title = "Edit Itinerary"
        binding.btnCreateItinerary.text = "Save"

        Glide.with(this)
            .load(itinerary.image)
            .placeholder(R.drawable.placeholder_image)
            .into(binding.ivDestinationPlaceholder)

        val colorMatrix = ColorMatrix().apply {
            setScale(0.8f, 0.8f, 0.8f, 1f)
        }
        binding.ivDestinationPlaceholder.colorFilter = ColorMatrixColorFilter(colorMatrix)
    }

    private fun setupDestinationView(destination: Destinations) {
        binding.tvDestinationName.text = destination.name
        binding.tvCityName.text = destination.location
        binding.tvContentDescription.text = destination.description
        binding.tvRating.text = destination.popularity
        binding.chipTypeDetail.text = destination.type
        binding.tvActivites.text = destination.type

        Glide.with(this)
            .load(destination.image)
            .into(binding.ivDestinationPlaceholder)

        val colorMatrix = ColorMatrix().apply {
            setScale(0.8f, 0.8f, 0.8f, 1f)
        }
        binding.ivDestinationPlaceholder.colorFilter = ColorMatrixColorFilter(colorMatrix)

        lifecycleScope.launch {
            val token = "Bearer ${viewModel.getToken()}"
            viewModel.getDestinationDetail(destination.id, token, "", "")
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
