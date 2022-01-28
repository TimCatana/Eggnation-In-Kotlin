package com.applicnation.eggnationkotlin.utils

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.applicnation.eggnationkotlin.R
import com.google.android.material.snackbar.Snackbar

open class BaseActivity : AppCompatActivity() {

//    private var doubleBackToExitPressedOnce = false
//
//    private lateinit var progressDialog: Dialog
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_base)
//    }
//
//    fun showErrorSnackBar(message: String, errorMessage: Boolean) {
//        val snackBar = Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
//        val snackBarView = snackBar.view
//
//        if(errorMessage) {
//            snackBarView.setBackgroundColor(
//                ContextCompat.getColor(
//                    this@BaseActivity,
//                    R.color.black // set to error color
//                )
//            )
//        } else {
//            snackBarView.setBackgroundColor(
//                ContextCompat.getColor(
//                    this@BaseActivity,
//                    R.color.black // set to success color
//                )
//            )
//        }
//
//        snackBar.show()
//    }
//
//    fun showProgressDialog(text: String) {
//        progressDialog = Dialog(this)
//
//        progressDialog.setContentView(R.layout.dialogue_progress)
//
//        progressDialog.findViewById<TextView>(R.id.tv_progress_text).text = text
//
//        progressDialog.setCancelable(false)
//
//        progressDialog.show()
//    }
//
//    fun hideProgressDialog() {
//        progressDialog.hide()
//    }
//
//    fun doubleBackToExit() {
//
//        if(doubleBackToExitPressedOnce) {
//            super.onBackPressed()
//            return
//        }
//
//        this.doubleBackToExitPressedOnce = true
//
//        Toast.makeText(this, "Please click again to exit?", Toast.LENGTH_SHORT).show()
//
//        @Suppress("DEPRECATION")
//        Handler().postDelayed({doubleBackToExitPressedOnce = false}, 2000)
//
//    }

}