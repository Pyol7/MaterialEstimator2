package com.example.materialestimator.views

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.materialestimator.MainActivity
import com.example.materialestimator.R
import com.example.materialestimator.TAG
import com.example.materialestimator.models.entities.Project
import com.example.materialestimator.viewModels.ProjectsViewModel
import com.example.materialestimator.viewModels.SharedViewModel
import com.google.android.material.appbar.CollapsingToolbarLayout

class ProjectFragment : Fragment(R.layout.fragment_project) {
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var project: Project
    private lateinit var pieChart: CustomPieChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        project = sharedViewModel.selectedProject
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val collapsingToolbarLayout: CollapsingToolbarLayout =
            view.findViewById(R.id.project_collapsing_toolbar)
        val toolbar = view.findViewById<Toolbar>(R.id.project_toolbar)
        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        setHasOptionsMenu(true)
        // Get the pie chart view
//        val pieChartView = view.findViewById(R.id.pie_chart) as PieChart
        // build the pie chart
//        pieChart = CustomPieChart(requireContext(), pieChartView)

        // Set backdrop image
        val backdropIV = collapsingToolbarLayout.findViewById<ImageView>(R.id.project_image_iv)
        val imgId = context?.resources?.getIdentifier(
            project.image,
            "drawable",
            context?.packageName
        ) as Int
        backdropIV.setImageResource(imgId)
        // Set title
        collapsingToolbarLayout.title = project.name

        val taskView = view.findViewById(R.id.tasks_cv) as View
        taskView.setOnClickListener {
            findNavController().navigate(R.id.action_projectFragment_to_tasksFragment)
        }

    }

}
