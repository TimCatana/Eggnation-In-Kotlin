package com.applicnation.eggnationkotlin.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.applicnation.eggnationkotlin.R
import com.applicnation.eggnationkotlin.models.Skin


class WonPrizeAdapter(val context: Context, val skins: ArrayList<Skin>): RecyclerView.Adapter<WonPrizeAdapter.ViewHolder>() {



    /**
     * Creates the viewHolder for an individual item.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WonPrizeAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_skin,
                parent,
                false
            )
        )
    }

    /**
     * Sets the data for the items in each viewHolder
     */
    override fun onBindViewHolder(holder: WonPrizeAdapter.ViewHolder, position: Int) {
        val skin = skins.get(index = position)

        holder.skinName.text = skin.skinName
    }

    override fun getItemCount(): Int {
        return skins.size
    }

    // TODO - use binding to get items
    // TODO - change image as well
    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val cardViewItem = view.findViewById<CardView>(R.id.skinItemCV)
        val skinName = view.findViewById<TextView>(R.id.skinNameTV)
    }




}