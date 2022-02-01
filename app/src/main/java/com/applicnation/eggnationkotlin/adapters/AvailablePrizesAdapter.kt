package com.applicnation.eggnationkotlin.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.applicnation.eggnationkotlin.R
import com.applicnation.eggnationkotlin.models.Prize
import com.applicnation.eggnationkotlin.models.Skin


class AvailablePrizesAdapter(val context: Context, val prizes: ArrayList<Prize>): RecyclerView.Adapter<AvailablePrizesAdapter.ViewHolder>() {

    /**
     * Creates the viewHolder for an individual item.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_available_prize,
                parent,
                false
            )
        )
    }

    /**
     * Sets the data for the items in each viewHolder
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val prize = prizes.get(index = position)
        holder.prizeName.text = prize.prizeName
    }

    override fun getItemCount(): Int {
        return prizes.size
    }

    // TODO - use binding to get items
    // TODO - change image as well
    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val prizeName = view.findViewById<TextView>(R.id.tvPrizeName)
    }




}