package com.applicnation.eggnationkotlin.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.applicnation.eggnationkotlin.R
import com.applicnation.eggnationkotlin.adapters.SkinsAdapter
import com.applicnation.eggnationkotlin.Supplier

class StoreActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store)

        setUpSkinRecyclerView()



    }

    private fun setUpSkinRecyclerView() {
        val layoutManager = LinearLayoutManager(this@StoreActivity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        findViewById<RecyclerView>(R.id.storeRecyclerView).layoutManager = layoutManager

        val adapter = SkinsAdapter(this@StoreActivity, Supplier.skins)

        findViewById<RecyclerView>(R.id.storeRecyclerView).adapter = adapter
    }
}
