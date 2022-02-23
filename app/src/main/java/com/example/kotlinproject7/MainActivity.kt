package com.example.kotlinproject7

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {

    private val resultTextView : TextView by lazy{
        findViewById<TextView>(R.id.resultTextView)
    }
    private val fireBaseTokenTextView : TextView by lazy{
        findViewById<TextView>(R.id.fireBaseTokenTextView)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initPushMesaageWithFireBase()


    }

    private fun initPushMesaageWithFireBase(){
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task ->
                if(task.isSuccessful){
                    fireBaseTokenTextView.text = task.result
                }
            }
    }
}