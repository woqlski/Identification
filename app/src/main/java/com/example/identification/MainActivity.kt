package com.example.identification

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        simulateButton.setOnClickListener {
            val number = numberInput.text
            val intent = Intent(this, ResultActivity::class.java)
            intent.putExtra("number", number.toString().toInt())
            startActivity(intent)
        }
    }
}