package com.example.assignment3_hansenbillyramades.presentation.view.activity

import android.content.DialogInterface
import android.content.Intent
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.assignment3_hansenbillyramades.R
import com.example.assignment3_hansenbillyramades.data.source.local.ItineraryEntity
import com.example.assignment3_hansenbillyramades.data.source.local.TravelMateDatabase
import com.example.assignment3_hansenbillyramades.databinding.ActivityDetailItineraryBinding
import com.example.assignment3_hansenbillyramades.presentation.viewModel.ItineraryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailItineraryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailItineraryBinding
    private lateinit var itinerary: ItineraryEntity
    private lateinit var db: TravelMateDatabase

    private val viewModel: ItineraryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailItineraryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = TravelMateDatabase.getDatabase(this)

        setupToolbar()
        loadData()
        setupEditButton()
        setupDeleteButton()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbarDetailItinerary)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setHomeAsUpIndicator(R.drawable.baseline_chevron_left_32)
            title = "Detail Itinerary"
        }
    }

    private fun loadData() {
        itinerary = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("itinerary", ItineraryEntity::class.java)!!
        } else {
            intent.getParcelableExtra("itinerary")!!
        }

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

    private fun setupEditButton() {
        binding.btnEdit.setOnClickListener {
            val editIntent = Intent(this, CreateItineraryActivity::class.java).apply {
                putExtra("isEdit", true)
                putExtra("itinerary", itinerary)
            }
            startActivity(editIntent)
        }
    }

    private fun setupDeleteButton() {
        binding.btnDelete.setOnClickListener {

            val alertDialog = AlertDialog.Builder(this@DetailItineraryActivity)
            alertDialog.setTitle("Delete Itinerary")
            alertDialog.setMessage("Are you sure want to delete Itinerary ${itinerary.destinationName}")

            alertDialog.setPositiveButton("Yes") { dialog: DialogInterface, which: Int ->
            lifecycleScope.launch {
                viewModel.deleteItinerary(itinerary)
            }
                Toast.makeText(
                    this@DetailItineraryActivity,
                    "Itinerary deleted!",
                    Toast.LENGTH_SHORT
                ).show()
                dialog.dismiss()
                finish()
            }

            alertDialog.setNegativeButton("No") { dialog: DialogInterface, which: Int ->
                dialog.dismiss()
            }
            alertDialog.setCancelable(false)
            alertDialog.show()

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
