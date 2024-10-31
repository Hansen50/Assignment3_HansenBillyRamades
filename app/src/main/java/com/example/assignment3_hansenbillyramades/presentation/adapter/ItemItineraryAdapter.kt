package com.example.assignment3_hansenbillyramades.presentation.adapter

import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.assignment3_hansenbillyramades.R
import com.example.assignment3_hansenbillyramades.data.source.local.ItineraryEntity
import com.example.assignment3_hansenbillyramades.databinding.ListItemMyTripBinding
import com.example.assignment3_hansenbillyramades.presentation.listener.ListItineraryListener

class ItemItineraryAdapter(
    private var listItinerary: List<ItineraryEntity>,
    private val listener: ListItineraryListener
) : RecyclerView.Adapter<ItemItineraryAdapter.MyViewHolder>() {
    class MyViewHolder(val binding: ListItemMyTripBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ListItemMyTripBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount() = listItinerary.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val itinerary = listItinerary[position]

        holder.binding.tvDestinationTrip.text = itinerary.destinationName
        holder.binding.tvCityTrip.text = itinerary.cityName

        Glide.with(holder.itemView.context)
            .load(itinerary.image)
            .placeholder(R.drawable.placeholder_image)
            .into(holder.binding.ibMyTrip)

        val colorMatrix = ColorMatrix().apply {
            setScale(0.8f, 0.8f, 0.8f, 1f)
        }
        holder.binding.ibMyTrip.colorFilter = ColorMatrixColorFilter(colorMatrix)

        holder.binding.ibMyTrip.setOnClickListener {
            listener.onClick(itinerary)
        }
    }

    fun updateData(newItinerary: List<ItineraryEntity>) {
        listItinerary = newItinerary
        notifyDataSetChanged()
    }
}



