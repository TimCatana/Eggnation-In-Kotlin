package com.applicnation.eggnationkotlin.authStack

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.applicnation.eggnationkotlin.R
import com.google.firebase.auth.FirebaseAuth

/**
 * A simple [Fragment] subclass.
 * Use the [ForgotPasswordFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ForgotPasswordFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        val view = inflater.inflate(R.layout.fragment_forgot_password, container, false)

        // TODO = Do stuff to UI using view

        return view;
    }
}



//
//override fun onCreate(savedInstanceState: Bundle?) {
//    super.onCreate(savedInstanceState)
//    setContentView(R.layout.activity_forgot_password)
//
//
//    var btnforgot = findViewById<Button>(R.id.btn_forgot_password)
//
//    btnforgot.setOnClickListener {
//        val email = findViewById<EditText>(R.id.tv_forgot_password).toString()
//        // check if email is empty and give a warning
//        // show progress dialog to show that something is happening
//
//        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
//            .addOnCompleteListener {
//
//                if(it.isSuccessful) {
//                    Toast.makeText(this@ForgotPasswordActivity, "Email sent!", Toast.LENGTH_LONG).show()
//                    finish() // goes back to login screen, finishes this activity
//                } else {
//                    // show an error
//                }
//            }
//    }
//
//}