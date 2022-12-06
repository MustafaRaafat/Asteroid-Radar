package com.udacity.asteroidradar.main

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.MainItemBinding

class MainRecycler : RecyclerView.Adapter<MainRecycler.ViewHolder>() {

    private var data = listOf<Asteroid>()
    fun setData(data: List<Asteroid>) {
        this.data = data
        notifyDataSetChanged()
    }

    class ViewHolder private constructor(val binding: MainItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val title: TextView
        val card: CardView

        init {
            title = binding.itemTitle
            card = binding.cardView
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layout = LayoutInflater.from(parent.context)
                val binding = MainItemBinding.inflate(layout, parent, false)
                return ViewHolder(binding)
            }
        }

        fun bind(item: Asteroid) {
            title.text = item.codename
            card.setOnClickListener {
                val a = MainFragmentDirections.actionShowDetail(item)
                card.findNavController().navigate(a)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }


    override fun getItemCount(): Int {
        return data.size
    }
}