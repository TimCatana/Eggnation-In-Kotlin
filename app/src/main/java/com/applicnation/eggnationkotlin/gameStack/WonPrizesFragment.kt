package com.applicnation.eggnationkotlin.gameStack

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.applicnation.eggnationkotlin.R

/**
 * A simple [Fragment] subclass.
 * Use the [WonPrizesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WonPrizesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_won_prizes, container, false)

        // TODO = Do stuff to UI using view

        return view;
    }
}