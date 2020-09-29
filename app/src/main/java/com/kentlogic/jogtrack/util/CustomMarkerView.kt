package com.kentlogic.jogtrack.util

import android.content.Context
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import com.kentlogic.jogtrack.db.Jog
import kotlinx.android.synthetic.main.marker_view.view.*
import java.text.SimpleDateFormat
import java.util.*

class CustomMarkerView (
    val jogs: List<Jog>,
    c: Context,
    layoutId: Int
) : MarkerView(c, layoutId) {

    //Set the offset of the pop-up window and it's position
    override fun getOffset(): MPPointF {
        //From MPAndroid chart documentation
        return MPPointF(-width / 1f, -height.toFloat())
    }

    override fun refreshContent(e: Entry?, highlight: Highlight?) {
        super.refreshContent(e, highlight)
        if(e == null) {
            return
        }
        //x values = indices on the mapped value jogs
        val currentJogId = e.x.toInt()
        val jog = jogs[currentJogId]

        val calendar = Calendar.getInstance().apply {
            timeInMillis = jog.timestamp
        }
        val dateFormat = SimpleDateFormat("MM-dd-YY", Locale.getDefault())
        tvDate.text = dateFormat.format(calendar.time)

        val avg = "${jog.avgSpeedKmh}km/h"
        tvAvgSpeed.text = avg

        val distanceInKm = "${jog.distanceInMeters/1000f}km"
        tvDistance.text = distanceInKm

        tvDuration.text = TrackingUtility.getFormattedStopWatchTime(jog.timeInMillis)

        val caloriesBurned = "${jog.caloriesBurned}kcal"
        tvCaloriesBurned.text = caloriesBurned

    }

}