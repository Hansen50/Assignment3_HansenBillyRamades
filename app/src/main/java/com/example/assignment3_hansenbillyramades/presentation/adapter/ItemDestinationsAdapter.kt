package com.example.assignment3_hansenbillyramades.presentation.adapter

import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.assignment3_hansenbillyramades.R
import com.example.assignment3_hansenbillyramades.databinding.ListItemDestinationsBinding
import com.example.assignment3_hansenbillyramades.domain.model.Destinations
import com.example.assignment3_hansenbillyramades.presentation.listener.ListDestinationListener

class ItemDestinationsAdapter(
    private var listDestination: List<Destinations>,
    private val listener: ListDestinationListener,
) :
    RecyclerView.Adapter<ItemDestinationsAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: ListItemDestinationsBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ListItemDestinationsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount() = listDestination.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val destination = listDestination[position]

        holder.binding.textTitle.text = destination.name
        holder.binding.chipType.text = destination.type
        holder.binding.textContent.text = destination.location
        holder.binding.tvRating.text = destination.popularity

        Glide.with(holder.itemView.context)
            .load(destination.image)
            .placeholder(R.drawable.placeholder_image)
            .into(holder.binding.ivDestination)

        val colorMatrix = ColorMatrix().apply {
            setScale(0.8f, 0.8f, 0.8f, 1f)
        }
        holder.binding.ivDestination.colorFilter = ColorMatrixColorFilter(colorMatrix)

        holder.binding.root.setOnClickListener {
            listDestination[position]?.id?.let { idDestination -> listener.onClick(idDestination) }
        }
    }


    fun updateDestinations(newDestination: List<Destinations>) {
        listDestination = newDestination
        notifyDataSetChanged()
    }

    // menambhakn daftar list apabila load baru
    fun addDestinations(newDestinations: List<Destinations>) {
        val startPosition = listDestination.size
        val updatedList = listDestination.toMutableList()
        updatedList.addAll(newDestinations)
        listDestination = updatedList
        notifyItemRangeInserted(startPosition, newDestinations.size)
        Log.d("ItemDestinationsAdapter", "Added ${newDestinations.size} items, total: ${listDestination.size}")
    }

}


