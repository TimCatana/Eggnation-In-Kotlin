package com.applicnation.eggnationkotlin.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.applicnation.eggnationkotlin.R
import com.applicnation.eggnationkotlin.models.Skin


class SkinsAdapter(val context: Context, val skins: ArrayList<Skin>): RecyclerView.Adapter<SkinsAdapter.ViewHolder>() {



    /**
     * Creates the viewHolder for an individual item.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
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
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val skin = skins.get(index = position)

        holder.ivSkin.setImageResource(skin.skinImage)
    }

    override fun getItemCount(): Int {
        return skins.size
    }

    // TODO - use binding to get items
    // TODO - change image as well
    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val cardViewItem = view.findViewById<CardView>(R.id.skinItemCV)
        val ivSkin = view.findViewById<ImageView>(R.id.ivSkin)
    }




}