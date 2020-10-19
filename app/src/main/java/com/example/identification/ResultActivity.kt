package com.example.identification

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jjoe64.graphview.LegendRenderer
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import kotlinx.android.synthetic.main.activity_result.*
import kotlin.math.pow


class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val a = 2
        val b = 3
        val n = intent.getIntExtra("number", 0)
        val u: DoubleArray
        val y: DoubleArray
        val generated = LineGraphSeries<DataPoint>()
        val calculated = LineGraphSeries<DataPoint>()
        val qSeries = LineGraphSeries<DataPoint>()
        val aCalc: Double
        val bCalc: Double
        var sumProdUY = 0.0
        var sumU = 0.0
        var sumY = 0.0
        var sumPowA = 0.0
        var sumQ = 0.0

        u = DoubleArray(n) { i -> i.toDouble() }
//        y = DoubleArray(n) { i -> ((i)*a + b).toDouble()}
        y = DoubleArray(n) { (-10..10).random().toDouble() }

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
        qSeries.title = "Q=%.2f".format(sumQ/n)
        graph.addSeries(qSeries)

        graph.legendRenderer.isVisible = true
        graph.legendRenderer.align = LegendRenderer.LegendAlign.TOP

    }
}