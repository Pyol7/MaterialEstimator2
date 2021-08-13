package com.example.materialestimator.views

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.materialestimator.R

class HomeFragment : Fragment(R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Views
        val projectsCv = view.findViewById(R.id.home_projects_cv) as View
        val materialsCv = view.findViewById(R.id.home_materials_cv) as View
        val employeesCv = view.findViewById(R.id.home_employees_cv) as View
        val toolsCv = view.findViewById(R.id.home_tools_cv) as View

        materialsCv.setOnClickListener {
            findNavController().navigate(R.id.action_global_materialsFragment)
        }

        projectsCv.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_projectsFragment)
        }

        employeesCv.setOnClickListener {
            findNavController().navigate(R.id.action_global_employeesFragment)
        }

        toolsCv.setOnClickListener {
            findNavController().navigate(R.id.action_global_toolsFragment)
        }

    }
}
