package com.example.materialestimator.views

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.materialestimator.MainActivity
import com.example.materialestimator.R
import com.example.materialestimator.models.entities.Project
import com.example.materialestimator.viewModels.ProjectsViewModel
import com.google.android.material.appbar.CollapsingToolbarLayout

class ProjectFragment : Fragment(R.layout.fragment_project) {
    private val projectsVm: ProjectsViewModel by activityViewModels()
    private lateinit var toolbar: Toolbar
    private lateinit var pieChart: CustomPieChart

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Get toolbar
        // Get toolbar
        val collapsingToolbarLayout: CollapsingToolbarLayout =
            view.findViewById(R.id.project_collapsing_toolbar)
        toolbar = view.findViewById(R.id.project_toolbar)
        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        setHasOptionsMenu(true)
        // Get the pie chart view
//        val pieChartView = view.findViewById(R.id.pie_chart) as PieChart
        // build the pie chart
//        pieChart = CustomPieChart(requireContext(), pieChartView)


        // Get and observe the Project clicked
        val projectID = arguments?.getInt("Key")
        projectsVm.get(projectID).observe(viewLifecycleOwner) { project ->
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
                val bundle = bundleOf("Key" to project.ID)
                findNavController().navigate(R.id.action_projectFragment_to_tasksFragment, bundle)
            }

        }

    }

}
