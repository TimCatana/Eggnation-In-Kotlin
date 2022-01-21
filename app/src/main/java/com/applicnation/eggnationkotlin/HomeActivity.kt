package com.applicnation.eggnationkotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue

class HomeActivity : AppCompatActivity() {
    private var mInterstitialAd: InterstitialAd? = null
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        MobileAds.initialize(this@HomeActivity)

        loadInterAd()

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference()


        val updates: MutableMap<String, Any> = HashMap()
        updates["globalCount"] = ServerValue.increment(1)

        var logoutBtn = findViewById<Button>(R.id.logoutBtn)
        var showAdBtn = findViewById<Button>(R.id.adBtn)

        logoutBtn.setOnClickListener {
            database.updateChildren(updates)

            auth.signOut()
            startActivity(Intent(this@HomeActivity, LoginActivity::class.java))
            finish()
        }

        showAdBtn.setOnClickListener {
            showInterAd()
        }


    }



    private fun showInterAd() {
        if (mInterstitialAd != null) {
            mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                    super.onAdFailedToShowFullScreenContent(p0)
                }

                override fun onAdShowedFullScreenContent() {
                    super.onAdShowedFullScreenContent()
                }

                override fun onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent()
                    Toast.makeText(this@HomeActivity, "Ad dismissed", Toast.LENGTH_LONG).show()
                    loadInterAd()
                }

                override fun onAdImpression() {
                    super.onAdImpression()
                }

                override fun onAdClicked() {
                    super.onAdClicked()
                }

            }

            mInterstitialAd?.show(this@HomeActivity)






        } else {
            Toast.makeText(this@HomeActivity, "Ad wasn't ready to show yet", Toast.LENGTH_LONG).show()
        }
    }



    private fun loadInterAd() {
        var adRequest = AdRequest.Builder().build()

        InterstitialAd.load(this,"ca-app-pub-3940256099942544/1033173712", adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                mInterstitialAd = interstitialAd
            }
        })
    }














    private fun loadAd(adRequest: AdRequest): InterstitialAd? {
         var loadedAd: InterstitialAd? = null

        InterstitialAd.load(this,"ca-app-pub-3940256099942544/1033173712", adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                Toast.makeText(this@HomeActivity, "Failed to load ad", Toast.LENGTH_SHORT).show()
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                loadedAd = interstitialAd
                Toast.makeText(this@HomeActivity, "Loaded Ad", Toast.LENGTH_SHORT).show()
            }
        })

        return loadedAd
    }


}