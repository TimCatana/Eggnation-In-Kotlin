package com.applicnation.eggnationkotlin.gameStack

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.applicnation.eggnationkotlin.R

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // TODO = Do stuff to UI using view

        return view;

    }

}



//
////    companion object {
////        val message: String = "This is HomeActivity Companion Object"
////    }
//
//
//    private lateinit var binding: ActivityHomeBinding
//
//    private var mInterstitialAd: InterstitialAd? = null
//    private lateinit var auth: FirebaseAuth
//    private lateinit var database: DatabaseReference
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityHomeBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//
//        MobileAds.initialize(this@HomeActivity)
//
//        auth = FirebaseAuth.getInstance()
//        database = FirebaseDatabase.getInstance().getReference()
//
//
//        val updates: MutableMap<String, Any> = HashMap()
//        updates["globalCount"] = ServerValue.increment(1)
//
//        var logoutBtn = findViewById<Button>(R.id.logoutBtn)
//        var showAdBtn = findViewById<Button>(R.id.adBtn)
//
//        logoutBtn.setOnClickListener {
//            database.updateChildren(updates)
//
//            auth.signOut()
//            startActivity(Intent(this@HomeActivity, LoginActivity::class.java))
//            finish()
//        }
//
//        showAdBtn.setOnClickListener {
//
//            showInterAd()
//        }
//
//
//
//        binding.storeBtn.setOnClickListener {
//            startActivity(Intent(this@HomeActivity, StoreActivity::class.java))
//        }
//
//
//    }
//
//
//
//    private fun showInterAd() {
//        if (mInterstitialAd != null) {
//            mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
//                override fun onAdFailedToShowFullScreenContent(p0: AdError) {
//                    super.onAdFailedToShowFullScreenContent(p0)
//                }
//
//                override fun onAdShowedFullScreenContent() {
//                    super.onAdShowedFullScreenContent()
//                }
//
//                override fun onAdDismissedFullScreenContent() {
//                    super.onAdDismissedFullScreenContent()
//                    Toast.makeText(this@HomeActivity, "Ad dismissed", Toast.LENGTH_LONG).show()
//                    loadInterAd()
//                }
//
//                override fun onAdImpression() {
//                    super.onAdImpression()
//                }
//
//                override fun onAdClicked() {
//                    super.onAdClicked()
//                }
//
//            }
//
//            mInterstitialAd?.show(this@HomeActivity)
//        } else {
//            loadInterAd()
//            Toast.makeText(this@HomeActivity, "Ad wasn't ready to show yet", Toast.LENGTH_LONG).show()
//        }
//    }
//
//
//
//    private fun loadInterAd() {
//        var adRequest = AdRequest.Builder().build()
//
//        InterstitialAd.load(this,"ca-app-pub-3940256099942544/1033173712", adRequest, object : InterstitialAdLoadCallback() {
//            override fun onAdFailedToLoad(adError: LoadAdError) {
//                mInterstitialAd = null
//            }
//
//            override fun onAdLoaded(interstitialAd: InterstitialAd) {
//                mInterstitialAd = interstitialAd
//            }
//        })
//    }
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//    private fun loadAd(adRequest: AdRequest): InterstitialAd? {
//         var loadedAd: InterstitialAd? = null
//
//        InterstitialAd.load(this,"ca-app-pub-3940256099942544/1033173712", adRequest, object : InterstitialAdLoadCallback() {
//            override fun onAdFailedToLoad(adError: LoadAdError) {
//                Toast.makeText(this@HomeActivity, "Failed to load ad", Toast.LENGTH_SHORT).show()
//            }
//
//            override fun onAdLoaded(interstitialAd: InterstitialAd) {
//                loadedAd = interstitialAd
//                Toast.makeText(this@HomeActivity, "Loaded Ad", Toast.LENGTH_SHORT).show()
//            }
//        })
//
//        return loadedAd
//    }
//
//