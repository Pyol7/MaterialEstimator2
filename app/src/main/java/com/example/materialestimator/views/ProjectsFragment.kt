package com.example.materialestimator.views

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.materialestimator.R
import com.example.materialestimator.TAG
import com.example.materialestimator.adapters.ProjectsFragmentRVAdapter
import com.example.materialestimator.viewModels.ProjectsViewModel
import com.example.materialestimator.viewModels.SharedViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * Displays all projects
 */
class ProjectsFragment : Fragment(R.layout.fragment_projects) {
    private val projectsVm: ProjectsViewModel by viewModels()
    private lateinit var addFab: FloatingActionButton

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Views
        val toolbar = view.findViewById(R.id.toolbar_main) as Toolbar
        val rv = view.findViewById(R.id.projects_rv) as RecyclerView
        addFab = view.findViewById(R.id.projects_add_fab)
        // Toolbar
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
        // Adapter
        val rvAdapter = ProjectsFragmentRVAdapter()
        rvAdapter.setOnItemClickListener(object : ProjectsFragmentRVAdapter.OnItemClickListener {
            override fun onItemClick(projectId: Long?) {
                // Show projectFragment
                arguments = bundleOf("key" to projectId)
                findNavController().navigate(R.id.action_projectsFragment_to_projectFragment, arguments)
            }
        })
        // RecyclerView
        rv.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = rvAdapter
            adapter?.stateRestorationPolicy =
                RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        }
        // Load all projects
        projectsVm.getAllProjectsWithTasks().observe(viewLifecycleOwner) {
            rvAdapter.setProjects(it)
        }
        // FAB
        addFab.setOnClickListener {
            // Create new project
            Log.i(TAG, "Create new project...")
        }

    }
}