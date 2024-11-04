package com.example.assignment3_hansenbillyramades.presentation.view.fragment

import android.content.Intent
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.assignment3_hansenbillyramades.data.source.local.ItineraryEntity
import com.example.assignment3_hansenbillyramades.databinding.FragmentItineraryBinding
import com.example.assignment3_hansenbillyramades.presentation.adapter.ItemItineraryAdapter
import com.example.assignment3_hansenbillyramades.presentation.listener.ListItineraryListener
import com.example.assignment3_hansenbillyramades.presentation.view.activity.DetailItineraryActivity
import com.example.assignment3_hansenbillyramades.presentation.viewModel.ItineraryViewModel

class ItineraryFragment : Fragment(), ListItineraryListener {
    private var _binding: FragmentItineraryBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ItemItineraryAdapter

    private val viewModel: ItineraryViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentItineraryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeViewModel()
        viewModel.loadItinerary()
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadItinerary()
    }

    private fun setupRecyclerView() {
        adapter = ItemItineraryAdapter(emptyList(), this)
        binding.rvItinerary.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvItinerary.adapter = adapter
    }

    private fun observeViewModel() {
        viewModel.itineraryList.observe(viewLifecycleOwner) { itinerary ->
            adapter.updateData(itinerary)
        }

        viewModel.isItineraryEmpty.observe(viewLifecycleOwner) { isEmpty ->
            binding.tvNoItinerary.visibility = if (isEmpty) View.VISIBLE else View.GONE
            binding.rvItinerary.visibility = if (isEmpty) View.GONE else View.VISIBLE
        }
    }

    override fun onClick(itinerary: ItineraryEntity) {
        val intent = Intent(requireContext(), DetailItineraryActivity::class.java).apply {
            putExtra("itinerary", itinerary)
        }
        startActivity(intent)
    }
}

