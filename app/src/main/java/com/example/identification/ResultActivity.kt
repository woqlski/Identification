package com.example.identification

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.jjoe64.graphview.LegendRenderer
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import kotlinx.android.synthetic.main.activity_result.*
import kotlin.math.pow


class ResultActivity : AppCompatActivity() {

    private lateinit var u: DoubleArray
    private lateinit var y: DoubleArray
    private val generated = LineGraphSeries<DataPoint>()
    private val calculated = LineGraphSeries<DataPoint>()
    private val qSeries = LineGraphSeries<DataPoint>()
    private var aCalc = 0.0
    private var bCalc = 0.0
    private var q = 0.0
    private var n = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideSystemUI()
        setContentView(R.layout.activity_result)

        n = intent.getIntExtra("number", 2)
        generateFunction()
        identifyFunction()
        displayGraph()
    }

    private fun generateFunction() {
        u = DoubleArray(n) { i -> i.toDouble() }
        y = DoubleArray(n) { (-10..10).random().toDouble() }
//        y = DoubleArray(n) { i -> ((i)*2 + 3).toDouble()}
    }

    private fun identifyFunction() {
        var sumProdUY = 0.0
        var sumU = 0.0
        var sumY = 0.0
        var sumPowA = 0.0
        var sumQ = 0.0

        for (i in 0 until n) {
            sumProdUY += u[i] * y[i]
            sumU += u[i]
            sumY += y[i]
            sumPowA += u[i].pow(2)
        }

        aCalc = ( n * sumProdUY - ( sumU * sumY ) ) /
                ( n * sumPowA - ( sumU * sumU ) )
        bCalc = ( sumY - ( aCalc * sumU ) ) / n

        for (i in 0 until n) {
            generated.appendData(DataPoint(u[i], y[i]), true, n)
            calculated.appendData(DataPoint(u[i], aCalc*u[i] + bCalc), true, n)
            sumQ += (y[i] - aCalc*u[i] - bCalc).pow(2)
        }

        q = sumQ / n
    }

    private fun displayGraph() {
        generated.title = "Generated"
        calculated.color = Color.BLUE
        generated.thickness = 20
        graph.addSeries(generated)

        val sign = if (bCalc >= 0) "+" else ""
        calculated.title = "Identificated: y=%.2f*x$sign%.2f".format(aCalc, bCalc)
        calculated.color = Color.RED
        calculated.thickness = 10
        graph.addSeries(calculated)

        qSeries.color = Color.GRAY
        qSeries.title = "Q=%.2f".format(q)
        graph.addSeries(qSeries)

        graph.legendRenderer.isVisible = true
        graph.legendRenderer.align = LegendRenderer.LegendAlign.TOP

        graph.viewport.setMaxX(n.toDouble())
        graph.viewport.isScalable = true
        graph.viewport.isScrollable = true
    }


    private fun hideSystemUI() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }
}