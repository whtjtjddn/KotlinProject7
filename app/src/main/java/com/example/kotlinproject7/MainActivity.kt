package com.example.kotlinproject7

import android.annotation.SuppressLint
import android.content.Intent
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

        initPushMessageWithFireBase()
        updateResult()

    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
        updateResult(true)
    }

    @SuppressLint("SetTextI18n")
    private fun updateResult(isNewIntent:Boolean = false){
      resultTextView.text = (intent.getStringExtra("notificationType") ?: "앱 런처") +if(isNewIntent) {
          "(으)로 갱신했습니다."
      }else{
          "(으)로 실행했습니다."
      }

    }
    private fun initPushMessageWithFireBase(){
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task ->
                if(task.isSuccessful){
                    fireBaseTokenTextView.text = task.result
                }
            }
    }
}