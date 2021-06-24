package com.example.materialestimator.views

import android.util.Log
import android.content.Context
import com.example.materialestimator.R
import com.example.materialestimator.TAG
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet

class CustomPieChart(var context: Context, private val pieChart: PieChart) {
    private var colors: ArrayList<Int> = arrayListOf()
    private var entries: ArrayList<PieEntry> = arrayListOf()

    enum class Labels{
        MATERIAL,
        LABOUR,
        TOOLS,
        MISC,
        PROFIT
    }

    init {
        setInitialEntries()
        setInitialColors()
        configurePieChart()
        refresh()
    }

    fun setNewEntry(value: Float, label: String){
        val e = PieEntry(value, label)
        Log.i(TAG, "value = $value, label = $label")
        replacePieEntry(e)
        refresh()
    }

    fun setProjectValue(value: Float){
        val e = PieEntry(value, Labels.PROFIT.name)
        entries[entries.size-1] = e
        refresh()
        Log.i(TAG, "entries = $entries")
    }

    fun setEntries(entries: ArrayList<PieEntry>){
        this.entries = entries

    }

    fun setColors(colors: ArrayList<Int>){
        this.colors = colors
    }

    private fun refresh(){
        pieChart.notifyDataSetChanged()
        pieChart.invalidate()
        pieChart.animateY(1000)
    }

    private fun setInitialEntries() {
        entries.add(PieEntry(0f, Labels.MATERIAL.name))
        entries.add(PieEntry(0f, Labels.LABOUR.name))
        entries.add(PieEntry(0f, Labels.TOOLS.name))
        entries.add(PieEntry(0f, Labels.MISC.name))
        entries.add(PieEntry(100f, Labels.PROFIT.name))
    }

    private fun setInitialColors() {
        colors.add(context.resources.getColor(R.color.pie_blue))
        colors.add(context.resources.getColor(R.color.pie_red))
        colors.add(context.resources.getColor(R.color.pie_orange))
        colors.add(context.resources.getColor(R.color.pie_purple))
        colors.add(context.resources.getColor(R.color.pie_green))
    }

    private fun configurePieChart() {
        // Configure the chart view
        pieChart.description.isEnabled = false
        pieChart.holeRadius = 40f
        pieChart.setDrawEntryLabels(false)
        pieChart.centerText = "Cost Overview"
        pieChart.setCenterTextSize(16f)
        pieChart.transparentCircleRadius = 45f
        pieChart.animateY(1000);
        // Disable the legend
        val l = pieChart.legend as Legend
        l.isEnabled = false
        // Create the data set object with no label
        val pieDataSet = PieDataSet(entries, "")
        pieDataSet.sliceSpace = 2f
        pieDataSet.setDrawValues(false)
        pieDataSet.colors = colors
        // Create the pie data set and add it to the pieChart
        pieChart.data = PieData(pieDataSet)
//        pieChart.invalidate() // refresh
    }

    /**
     * Replaces an entry and minuses/adds the difference to the Profit entry.
     * If the profit is < 0 then it is set to 0
     */
    private fun replacePieEntry(newEntry: PieEntry){
        var diff = 0f
        // Replace the entry with a label that corresponds to the new Entry label
        for ((index, value) in entries.withIndex()) {
            if (value.label == newEntry.label) {
                diff = value.value - newEntry.value
                entries[index] = newEntry
            }
            // Minus new entry value from profit
            if (value.label == Labels.PROFIT.name) {
                val newProfit = value.value + diff
                if (newProfit > 0) {
                    entries[index] = PieEntry(newProfit, value.label)
                } else {
                    entries[index] = PieEntry(0f, value.label)
                }
            }
        }
        Log.i(TAG, "entries = $entries")
    }

}