package com.example.materialestimator.views

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.materialestimator.R
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = view.findViewById(R.id.projects_toolbar) as Toolbar
        toolbar.inflateMenu(R.menu.general_toolbar_menu)
        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        setHasOptionsMenu(true)
        val rvAdapter = ProjectsFragmentAdapter()
        val rv = view.findViewById(R.id.projects_rv) as RecyclerView
        rv.apply {
            setHasFixedSize(true)
            rvAdapter.setOnItemClickListener(object : ProjectsFragmentAdapter.OnItemClickListener {
                override fun onItemClick(jsonProject: String) {
                    // Show project fragment
                    sharedViewModel.selectedProject = MoshiConverters.jsonToProject(jsonProject)!!
                    findNavController().navigate(R.id.action_projectsFragment_to_projectFragment)
                }

            })
            adapter = rvAdapter
            adapter?.stateRestorationPolicy =
                RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        }
        projectsVm.getAll().observe(viewLifecycleOwner) {
            rvAdapter.setProjects(it)
        }
        // Setup a listener on the fab to show the add project fragment
        val fab = view.findViewById(R.id.add_project_fab) as FloatingActionButton
        fab.setOnClickListener {
            Toast.makeText(context, "Create new project...", Toast.LENGTH_SHORT).show()
        }
    }
}