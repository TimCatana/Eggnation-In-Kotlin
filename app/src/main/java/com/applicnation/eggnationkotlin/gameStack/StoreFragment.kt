package com.applicnation.eggnationkotlin.gameStack

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.applicnation.eggnationkotlin.R
import com.applicnation.eggnationkotlin.models.SkinsSupplier
import com.applicnation.eggnationkotlin.databinding.FragmentStoreBinding
import com.applicnation.eggnationkotlin.utils.SkinAdapter

/**
 * A simple [Fragment] subclass.
 * Use the [StoreFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StoreFragment : Fragment(R.layout.fragment_store) {

    private lateinit var skinsRecyclerView: RecyclerView



    private var _binding: FragmentStoreBinding? = null
    private val binding
        get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentStoreBinding.bind(view)

        skinsRecyclerView = binding.skinsRV
        skinsRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        val skinAdapter = SkinAdapter(requireContext(), SkinsSupplier.skins)

        skinsRecyclerView.adapter = skinAdapter


    }

}
