package com.applicnation.eggnationkotlin.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.applicnation.eggnationkotlin.R
import com.applicnation.eggnationkotlin.models.Prize


class WonPrizeAdapter(val context: Context, val wonPrizes: ArrayList<Prize>): RecyclerView.Adapter<WonPrizeAdapter.ViewHolder>() {


    /**
     * Creates the viewHolder for an individual item.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_won_prize,
                parent,
                false
            )
        )
    }

    /**
     * Sets the data for the items in each viewHolder
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val wonPrize = wonPrizes.get(index = position)

        holder.prizeName.text = wonPrize.prizeName

        if(wonPrize.prizeType == "phone") {
            holder.prizeImage.setImageResource(R.drawable.egg_one)
        } else if (wonPrize.prizeType == "laptop") {
            holder.prizeImage.setImageResource(R.drawable.egg_two)
        } else {
            holder.prizeImage.setImageResource(R.drawable.egg_three)
        }
    }

    override fun getItemCount(): Int {
        return wonPrizes.size
    }

    // TODO - use binding to get items
    // TODO - change image as well
    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val prizeImage = view.findViewById<ImageView>(R.id.ivPrizeImage)
        val prizeName = view.findViewById<TextView>(R.id.tvPrizeName)
    }




}
