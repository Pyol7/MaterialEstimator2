package com.example.materialestimator.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.materialestimator.R
import com.example.materialestimator.viewModels.ProjectViewModel
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter

class ProjectFragment : Fragment() {
    private val projectVm: ProjectViewModel by activityViewModels()
    private lateinit var toolbar: Toolbar
    private lateinit var pieChart: CustomPieChart

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_project, container, false)
        // Use this fragment's toolbar and provide all functionality
        toolbar = view.findViewById(R.id.project_toolbar) as Toolbar
        toolbar.inflateMenu(R.menu.general_toolbar_menu)
        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        // Get the pie chart view
        val pieChartView = view.findViewById(R.id.pie_chart) as PieChart
        // build the pie chart
        pieChart = CustomPieChart(requireContext(), pieChartView)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Get and observe the Project clicked
        val id = arguments?.getInt("Key")
        projectVm.get(id).observe(viewLifecycleOwner) {
            toolbar.title = it.name

        }


    }


}
