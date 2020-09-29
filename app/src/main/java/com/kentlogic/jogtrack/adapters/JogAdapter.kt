package com.kentlogic.jogtrack.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kentlogic.jogtrack.R
import com.kentlogic.jogtrack.db.Jog
import com.kentlogic.jogtrack.util.TrackingUtility
import kotlinx.android.synthetic.main.item_jog.view.*
import java.text.SimpleDateFormat
import java.util.*

class JogAdapter: RecyclerView.Adapter<JogAdapter.JogViewHolder>() {

    val diffCallback = object : DiffUtil.ItemCallback<Jog>(){
        override fun areItemsTheSame(oldItem: Jog, newItem: Jog): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Jog, newItem: Jog): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JogViewHolder {
        return JogViewHolder (
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_jog,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: JogViewHolder, position: Int) {
        val jog = differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this).load(jog.img).into(ivRunImage)
            val calendar = Calendar.getInstance().apply {
                timeInMillis = jog.timestamp
            }
            val dateFormat = SimpleDateFormat("MM-dd-YY", Locale.getDefault())
            tvDate.text = dateFormat.format(calendar.time)

            val avg = "${jog.avgSpeedKmh}km/h"
            tvAvgSpeed.text = avg

            val distanceInKm = "${jog.distanceInMeters/1000f}km"
            tvDistance.text = distanceInKm

            tvTime.text = TrackingUtility.getFormattedStopWatchTime(jog.timeInMillis)

            val caloriesBurned = "${jog.caloriesBurned}kcal"
            tvCalories.text = caloriesBurned
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<Jog>) = differ.submitList(list)

    inner class JogViewHolder(view: View): RecyclerView.ViewHolder(view)
}