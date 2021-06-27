package com.example.materialestimator.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.materialestimator.R
import com.example.materialestimator.models.entities.Project
import com.example.materialestimator.viewModels.ProjectsViewModel
import com.github.mikephil.charting.charts.PieChart

class ProjectFragment : Fragment() {
    private val projectsVm: ProjectsViewModel by activityViewModels()
    private lateinit var toolbar: Toolbar
    private lateinit var pieChart: CustomPieChart
    lateinit var project: Project

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

        val taskView = view.findViewById(R.id.tasks_cv) as View
        taskView.setOnClickListener {
            val bundle = bundleOf("Key" to project.id)
            findNavController().navigate(R.id.action_projectFragment_to_taskFragment, bundle)
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Get and observe the Project clicked
        val projectID = arguments?.getInt("Key")
        projectsVm.get(projectID).observe(viewLifecycleOwner) {
            project = it
            toolbar.title = project.name

        }

    }

}
