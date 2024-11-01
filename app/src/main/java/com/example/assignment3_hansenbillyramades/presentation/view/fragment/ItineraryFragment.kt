package com.example.assignment3_hansenbillyramades.presentation.view.fragment

import android.content.Intent
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.assignment3_hansenbillyramades.data.source.local.ItineraryEntity
import com.example.assignment3_hansenbillyramades.data.source.local.TravelMateDatabase
import com.example.assignment3_hansenbillyramades.databinding.FragmentItineraryBinding
import com.example.assignment3_hansenbillyramades.presentation.adapter.ItemItineraryAdapter
import com.example.assignment3_hansenbillyramades.presentation.listener.ListItineraryListener
import com.example.assignment3_hansenbillyramades.presentation.view.activity.DetailItineraryActivity
import kotlinx.coroutines.launch

class ItineraryFragment : Fragment(), ListItineraryListener {
    private var _binding: FragmentItineraryBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ItemItineraryAdapter
    private lateinit var db: TravelMateDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentItineraryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = TravelMateDatabase.getDatabase(requireContext())

        setupRecyclerView()
        loadItinerary()
    }

    override fun onResume() {
        super.onResume()
        loadItinerary()
    }

    private fun setupRecyclerView() {
        adapter = ItemItineraryAdapter(emptyList(), this)
        binding.rvItinerary.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvItinerary.adapter = adapter
    }

    private fun loadItinerary() {
        lifecycleScope.launch {
            val itinerary = db.itineraryDao().getItinerary()
            adapter.updateData(itinerary)
        }
    }

    override fun onClick(itinerary: ItineraryEntity) {
        val intent = Intent(requireContext(), DetailItineraryActivity::class.java).apply {
            putExtra("itinerary", itinerary)
        }
        startActivity(intent)
    }
}
