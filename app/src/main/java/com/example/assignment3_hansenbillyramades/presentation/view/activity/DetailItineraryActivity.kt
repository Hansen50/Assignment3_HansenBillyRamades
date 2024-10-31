package com.example.assignment3_hansenbillyramades.presentation.view.activity

import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.assignment3_hansenbillyramades.R
import com.example.assignment3_hansenbillyramades.data.source.local.ItineraryEntity
import com.example.assignment3_hansenbillyramades.data.source.local.TravelMateDatabase
import com.example.assignment3_hansenbillyramades.databinding.ActivityDetailItineraryBinding

class DetailItineraryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailItineraryBinding
    private lateinit var itinerary: ItineraryEntity
    private lateinit var db: TravelMateDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailItineraryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = TravelMateDatabase.getDatabase(this)

        setSupportActionBar(binding.toolbarDetailItinerary)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.baseline_chevron_left_32)
        supportActionBar?.title = "Detail"

        itinerary = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            intent.getParcelableExtra("itinerary", ItineraryEntity::class.java)!!
        else
            intent.getParcelableExtra("itinerary")!!

        binding.tvDestinationName.text = itinerary.destinationName
        binding.tvCityName.text = itinerary.cityName
        binding.tvActivites.text = itinerary.activities
        binding.tvContentDescription.text = itinerary.description
        binding.tvTextNotes.text = itinerary.note
        binding.tvRating.text = itinerary.rating
        binding.chipTypeDetail.text = itinerary.type

        Glide.with(this)
            .load(itinerary.image)
            .placeholder(R.drawable.placeholder_image)
            .into(binding.ivDestinationPlaceholder)

        val colorMatrix = ColorMatrix().apply {
            setScale(0.8f, 0.8f, 0.8f, 1f)
        }
        binding.ivDestinationPlaceholder.colorFilter = ColorMatrixColorFilter(colorMatrix)
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