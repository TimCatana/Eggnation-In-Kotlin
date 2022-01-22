package com.applicnation.eggnationkotlin.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.applicnation.eggnationkotlin.R
import com.applicnation.eggnationkotlin.Skin

class SkinsAdapter(val context: Context, private val skins: List<Skin>) : RecyclerView.Adapter<SkinsAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val skin = skins[position]
        holder.setData(skin, position)
    }

    override fun getItemCount(): Int {
        return skins.size
    }


    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var currentSkin: Skin? = null
        var currentPosition: Int = 0

        init {
//            itemView.setOnClickListener {
//                Toast.makeText(context, "${currentSkin!!.skinTitle} Clicked!", Toast.LENGTH_SHORT ).show()
//            }

            itemView.findViewById<ImageView>(R.id.imgShare).setOnClickListener {
                currentSkin?.let {
                        Toast.makeText(context, "clicked share button for ${currentSkin!!.skinTitle}", Toast.LENGTH_SHORT ).show()
                }
            }

        }

        fun setData(skin: Skin?, pos: Int) {
            skin?.let {
                itemView.findViewById<TextView>(R.id.txvTitle).text = skin.skinTitle
            }


            this.currentSkin = skin
            this.currentPosition = pos

        }
    }
}