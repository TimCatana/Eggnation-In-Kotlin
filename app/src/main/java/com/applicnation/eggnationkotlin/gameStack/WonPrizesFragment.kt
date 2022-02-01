package com.applicnation.eggnationkotlin.gameStack

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.applicnation.eggnationkotlin.R
import com.applicnation.eggnationkotlin.databinding.FragmentWonPrizesBinding
import com.applicnation.eggnationkotlin.models.WonPrizeSupplier
import com.applicnation.eggnationkotlin.adapters.WonPrizeAdapter

/**
 * A simple [Fragment] subclass.
 * Use the [WonPrizesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WonPrizesFragment : Fragment(R.layout.fragment_won_prizes) {
    private lateinit var skinsRecyclerView: RecyclerView


    private var _binding: FragmentWonPrizesBinding? = null
    private val binding
        get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentWonPrizesBinding.bind(view)

        skinsRecyclerView = binding.skinsRV
        skinsRecyclerView.layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)

        val wonPrizesAdapter = WonPrizeAdapter(requireContext(), WonPrizeSupplier.wonPrizes)

        skinsRecyclerView.adapter = wonPrizesAdapter


    }
}