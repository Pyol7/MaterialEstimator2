package com.example.materialestimator.views

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.materialestimator.R
import com.example.materialestimator.TAG
import com.example.materialestimator.adapters.ProjectsFragmentAdapter
import com.example.materialestimator.utilities.MoshiConverters
import com.example.materialestimator.viewModels.ProjectsViewModel
import com.example.materialestimator.viewModels.SharedViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * Displays the list of projects
 */
class ProjectsFragment : Fragment(R.layout.fragment_projects) {
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val projectsVm: ProjectsViewModel by viewModels()
    private lateinit var addFab: FloatingActionButton

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Toolbar
        val toolbar = view.findViewById(R.id.toolbar_main) as Toolbar
        toolbar.setNavigationOnClickListener { findNavController().navigateUp() }
        toolbar.title = "Projects"
        toolbar.inflateMenu(R.menu.general_menu)
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_help -> {
                    Log.i(TAG, "Help...")
                    true
                }
                else -> false
            }
        }
        // Add FAB
        addFab = view.findViewById(R.id.projects_add_fab)
        addFab.setOnClickListener {
            // Create new project
            Log.i(TAG, "Create new project...")
        }
        // Adapter
        val rvAdapter = ProjectsFragmentAdapter()
        rvAdapter.setOnItemClickListener(object : ProjectsFragmentAdapter.OnItemClickListener {
            override fun onItemClick(jsonProject: String) {
                // Show project fragment
                sharedViewModel.selectedProject = MoshiConverters.jsonToProject(jsonProject)!!
                findNavController().navigate(R.id.action_projectsFragment_to_projectFragment)
            }

        })
        // RecyclerView
        val rv = view.findViewById(R.id.projects_rv) as RecyclerView
        rv.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = rvAdapter
            adapter?.stateRestorationPolicy =
                RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        }
        // RecyclerView Data
        projectsVm.getAll().observe(viewLifecycleOwner) {
            rvAdapter.setProjects(it)
        }

    }

}