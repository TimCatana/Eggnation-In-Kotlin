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


class SkinAdapter(val context: Context, val skins: ArrayList<Skin>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val VIEW_TYPE_ONE = 1
        const val VIEW_TYPE_TWO = 2
    }

    /**
     * Creates the viewHolder for an individual item.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType == VIEW_TYPE_ONE) {
            return ViewHolder(
                LayoutInflater.from(context).inflate(
                    R.layout.item_skin,
                    parent,
                    false
                )
            )
        } else {
            return AnotherViewHolder(
                LayoutInflater.from(context).inflate(
                    R.layout.item_skin, // TODO - change to a custom item viewtype
                    parent,
                    false
                )
            )
        }

    }

    /**
     * Sets the data for the items in each viewHolder
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val skin = skins.get(index = position)

        if(holder is ViewHolder) {
            holder.skinName.text = skin.skinName
        } else if (holder is AnotherViewHolder) {
            // custom view holder
        }
    }

    override fun getItemCount(): Int {
        return skins.size
    }

    // TODO - need to edit the skins model to add which viewtype you want to use
    override fun getItemViewType(position: Int): Int {
        return skins[position].viewType
    }

    // TODO - use binding to get items
    // TODO - change image as well
    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val cardViewItem = view.findViewById<CardView>(R.id.skinItemCV)
        val skinName = view.findViewById<TextView>(R.id.skinNameTV)
    }

    // TODO - use binding to get items
    // TODO - change image as well
    class AnotherViewHolder(view: View): RecyclerView.ViewHolder(view) {
        // In reality, this viewholder will most likely hold differend id's to different componentns in the new layout we want to use for this viewholder
        val cardViewItem = view.findViewById<CardView>(R.id.skinItemCV)
        val skinName = view.findViewById<TextView>(R.id.skinNameTV)
    }

    // TODO - to add another viewtype just add another viewholder and change what you want to modify in it




}