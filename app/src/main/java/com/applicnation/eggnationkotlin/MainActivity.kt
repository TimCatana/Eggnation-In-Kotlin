package com.applicnation.eggnationkotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()
    }

    // this will be the main routing system to determine wheterh the user is lgged in or not
    override fun onStart() {
        super.onStart();

        var mfirebaseUser: FirebaseUser? = auth?.currentUser;

        if(mfirebaseUser != null) {
            // go to home screen
            startActivity(Intent(this@MainActivity, HomeActivity::class.java))
            finish()
        } else {
            // go to login screen
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
            finish()
        }
    }

}





/*
    Pair
    Triple
    List
    Set
    Map
    Sequence
    for(i in lol) loops
    when - is - else (the kotlin switch case)
    var (mutable)
    val (not mutable)

    var x: string = "lol" (string type)
    var x = 2 (assumed int)

    Unit = basically 'void' in Javas

    fun myFunction(name: String, age: Int): Unit { (Unit is the return type, unit is basically void, so I don't need to add it
        ...
    }

     fun myFunctionTwo(name: String, age: Int): String { (This one has a string return type
        ...
    }

    -- both below work
    myFunction(name = "myName", age = 27)
    myFunction(age = 27, name = "myName")

    lazy (keyword for memoization)


    val lambdaFuncName: (inputTypeOne, inputTypeTwo) -> ReturnType = {argument: inputTypeOne, argumentTwo: inputTypeTwo -> body }

    val lambdaFuncName: (Int, Int) -> Int = {a: Int, b: Int -> a + b }


    -- below function infers the input anf output types
    val greetLambda = { name: String -> println("Hello $name") }


 */