package com.applicnation.eggnation.feature_eggnation.presentation.game.home

import android.content.Context
import android.content.ContextWrapper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.applicnation.eggnation.R
import com.applicnation.eggnation.feature_eggnation.data.remote.firebase.AdMob
import com.applicnation.eggnation.feature_eggnation.presentation.auth.forgot_password.ForgotPasswordScreenViewModel
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.interstitial.InterstitialAd

@Composable
fun HomeScreen(
    navController: NavController,
    adMob: AdMob,
    viewModel: HomeScreenViewModel = hiltViewModel()
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = viewModel.tapCounter.value.toString(),
            style = MaterialTheme.typography.h3
        )
        Image(
            // TODO - change the size of egg to make it feel natural
            // TODO - get rid of default tap animation
            // TODO - set id to the image in the datastore I want to make
            painter = painterResource(id = R.drawable.egg),
            contentDescription = "Tappable Egg",
            modifier = Modifier.clickable {
                viewModel.onEvent(HomeScreenEvent.DecrementCounter)

                if(viewModel.tapCounter.value % 5 == 0) {
                    viewModel.onEvent(HomeScreenEvent.PlayAd(adMob))
                } else {
                    viewModel.onEvent(HomeScreenEvent.LoadAd(adMob))
                }


                // TODO - add main game prize winning logic here
                // TODO - decrement counter here
                // TODO - add correct egg animation based on whether user won or not
            }
        )
    }
}


@Preview(showBackground = true)
@Composable
fun HomePreview() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "1000",
            style = MaterialTheme.typography.h3
        )
        Image(
            painter = painterResource(id = R.drawable.egg),
            contentDescription = "Tappable Egg",
            modifier = Modifier.clickable {

                // TODO - add main game prize winning logic here
                // TODO - decrement counter here
            }
        )
    }
}





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