package com.example.identification

import android.content.Intent
import android.os.Bundle
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        numberSeekBar.progress = 2
        numberSeekBar.max = 100
        numberInput.text = "2"

        simulateButton.setOnClickListener {
            val minimum = min.text.toString()
            val minVal = Integer.valueOf(minimum)
            val maximum = max.text.toString()
            val maxVal = Integer.valueOf(maximum)
            val intent = Intent(this, ResultActivity::class.java)
            intent.putExtra("number", numberSeekBar.progress)
            intent.putExtra("min", minVal)
            intent.putExtra("max", maxVal)
            startActivity(intent)
        }

        numberSeekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {

            override fun onProgressChanged(
                seekBar: SeekBar,
                progress: Int,
                fromUser: Boolean
            ) {
                if (progress < 3) numberSeekBar.progress = 2
                numberInput.text = progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

    }
}