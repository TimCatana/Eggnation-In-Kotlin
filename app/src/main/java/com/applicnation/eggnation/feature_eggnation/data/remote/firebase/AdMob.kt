package com.applicnation.eggnation.feature_eggnation.data.remote.firebase

import android.app.Activity
import android.content.Context
import android.util.Log
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import timber.log.Timber

/**
 * IMPORTANT NOTE FOR INTERSTITIAL ADS:
 *
 * You need to declare an instance of this class in the activity where you want the ads to be played.
 * That admob instance should pass the activity's context (usually "this") into the constructor.
 * Then, you need to pass that instance of the admob class to the composable you with to play ads
 * in.
 * Finally, you should somehow pass the entire admob instance to the viewModel which you can use
 * to load and play ads.
 *
 * WHY DO THE ABOVE?
 *
 * It's because we need the activity context to load and play ads. Passing the context around causes
 * memory leaks. In this way, we only ever declare one instance of the class that stays alive until
 * the activity that uses it is destroyed.
 *
 * NOTES:
 *
 * There might be a better way to do this using contextWrapper. But I am unfamiliar with how that
 * works and more importantly, how to incorporate it into this clean architecture model.
 */
class AdMob constructor(
    private val activityContext: Activity
) {
    private var interstitialAd: InterstitialAd? = null
    private val adRequest: AdRequest = AdRequest.Builder().build()


    fun loadInterstitialAd() {
        if (interstitialAd == null) {
            InterstitialAd.load(
                activityContext,
                "ca-app-pub-3940256099942544/1033173712", // TODO probably pass the ad Unit id as a constructor or something
                adRequest,
                object : InterstitialAdLoadCallback() {
                    override fun onAdFailedToLoad(adError: LoadAdError) {
                        interstitialAd = null
                        Timber.w("Interstitial Ad failed to load")
                    }

                    override fun onAdLoaded(ad: InterstitialAd) {
                        interstitialAd = ad
                        Timber.i("Interstitial Ad loaded successfully")
                    }
                })
        }
    }

    fun playInterstitialAd() {
        setInterstitialCallbacks()

        if (interstitialAd != null) {
            interstitialAd?.show(activityContext)
        } else {
            Timber.w("The interstitial ad was not loaded yet. Ad not played")
        }
    }

    private fun setInterstitialCallbacks() {
        interstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdImpression() {
                super.onAdImpression()  // TODO - probably keep track of clicks for marketing purposes. probably compare how many ads were showsn to how many were clicked and filter ads based on that?
            }

            override fun onAdShowedFullScreenContent() {
                Timber.i("Interstitial Ad showed in full screen")
                 // TODO - probably keep track of clicks for marketing purposes
            }

            override fun onAdDismissedFullScreenContent() {
                interstitialAd = null
                Timber.i("Interstitial Ad was dismissed")
            }

            override fun onAdClicked() {
                 // TODO - probably keep track of clicks for marketing purposes
                interstitialAd = null
                Timber.i("Interstitial Ad was clicked")
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
                interstitialAd = null
                Timber.w("Interstitial Ad failed to show")
            }
        }
    }

}