package com.example.materialestimator.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.materialestimator.R
import com.example.materialestimator.adapters.ProjectsFragmentAdapter
import com.example.materialestimator.viewModels.ProjectsViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * Displays the list of projects
 */
class ProjectsFragment : Fragment(R.layout.fragment_projects), ProjectsFragmentAdapter.OnItemClickListener {
    private val vm: ProjectsViewModel by viewModels()
    private var rvAdapter: ProjectsFragmentAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar = view.findViewById(R.id.projects_toolbar) as Toolbar
        toolbar.inflateMenu(R.menu.general_toolbar_menu)
        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        setHasOptionsMenu(true)

        val rv = view.findViewById(R.id.projects_rv) as RecyclerView
        rv.apply {
            setHasFixedSize(true)
            rvAdapter = ProjectsFragmentAdapter()
            rvAdapter!!.setOnItemClickListener(this@ProjectsFragment)
            adapter = rvAdapter
            adapter?.stateRestorationPolicy =
                RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        }

        vm.getAll().observe(viewLifecycleOwner) {
            rvAdapter?.setProjects(it)
        }

        // Setup a listener on the fab to show the add project fragment
        val fab = view.findViewById(R.id.add_project_fab) as FloatingActionButton
        fab.setOnClickListener {
            Toast.makeText(context, "Create new project...", Toast.LENGTH_SHORT).show()
        }
    }

//    fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return when (item.itemId) {
//            R.id.home -> {
//                // Provide functionality for the home icon.
//                // Used when enableDrawerNavigation = true
//                mainActivity.toggleDrawer()
//                true
//            }
//            else -> super.onOptionsItemSelected(item)
//        }
//    }

    override fun onItemClick(projectID: Int) {
        // Show project fragment
        val bundle = bundleOf("Key" to projectID)
        findNavController().navigate(R.id.action_projectsFragment_to_projectFragment, bundle)
    }

}