package com.example.materialestimator.views.project

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.materialestimator.R
import com.example.materialestimator.TAG
import com.example.materialestimator.storage.local.entities.Task
import com.example.materialestimator.storage.local.relationships.ProjectWithTasks
import com.example.materialestimator.viewModels.ProjectsViewModel
import com.example.materialestimator.viewModels.TasksViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Sets up the view pager and tabs that displays fragments containing
 * data associated with the Project.
 */

class ProjectFragment : Fragment(R.layout.fragment_project) {
    private val projectsVM: ProjectsViewModel by viewModels()
    private val tasksVM: TasksViewModel by viewModels()
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var addFab: FloatingActionButton
    private lateinit var projectWithTasks: ProjectWithTasks
    private var projectId = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // If there is a saved id then restore it, else use the one that was passed in.
        projectId = savedInstanceState?.getLong("key") ?: arguments?.getLong("key")!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Views
        val toolbar = view.findViewById(R.id.project_toolbar) as Toolbar
        val projectMainImageIV = view.findViewById(R.id.project_main_image_iv) as ImageView
        viewPager = view.findViewById(R.id.project_viewpager)
        addFab = view.findViewById(R.id.project_add_fab)
        tabLayout = view.findViewById(R.id.project_viewpager_tab_layout)
        // Toolbar
        toolbar.setNavigationOnClickListener { findNavController().navigateUp() }
        toolbar.inflateMenu(R.menu.project_menu)
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_settings -> {
                    Log.i(TAG, "Settings...")
                    true
                }
                R.id.action_help -> {
                    Log.i(TAG, "Help...")
                    true
                }
                else -> false
            }
        }
        // Get the selected project with it's tasks
        projectsVM.get(projectId).observe(viewLifecycleOwner) { project ->
            projectWithTasks = project
            // Toolbar title
            toolbar.title = projectWithTasks.project?.name
            // Project image
            val imgId = context?.resources?.getIdentifier(
                projectWithTasks.project?.image,
                "drawable",
                requireContext().packageName
            ) as Int
            projectMainImageIV.setImageResource(imgId)
            // ViewPager adapter
            viewPager.adapter = ProjectViewPagerAdapter(this)
            // TabLayout
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                when (position) {
                    0 -> tab.text = "Tasks"
                    1 -> tab.text = "Photos"
                    2 -> tab.text = "Files"
                    3 -> tab.text = "Notes"
                }
            }.attach()
        }
        // ViewPager
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrollStateChanged(state: Int) {
                when (state) {
                    ViewPager2.SCROLL_STATE_SETTLING -> addFab.show()
                    ViewPager2.SCROLL_STATE_IDLE -> addFab.show()
                    else -> addFab.hide()
                }
            }

            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> {
                        addFab.setOnClickListener {
                            val newTask = Task(projectId_fk = projectId)
                            /**
                             * Insert the new task into the db. Wait for the newly created id to
                             * return from the query, then navigate to TaskFragment for adding info.
                             */
                            tasksVM.insert(newTask).observe(viewLifecycleOwner) { id ->
                                arguments = bundleOf("key" to id)
                                findNavController().navigate(
                                    R.id.action_projectFragment_to_taskFragment, arguments
                                )
                            }
                        }
                    }
                    1 -> {
                        addFab.setOnClickListener {
                            Log.i(TAG, "Adding photo...")
                        }
                    }
                    2 -> {
                        addFab.setOnClickListener {
                            Log.i(TAG, "Adding file...")
                        }
                    }
                    3 -> {
                        addFab.setOnClickListener {
                            Log.i(TAG, "Adding note...")
                        }
                    }
                }
            }
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong("key", projectId)
    }

    // ViewPagerAdapter @param fragment - the container fragment that holds the ViewPager2 view
    inner class ProjectViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int {
            return 4
        }

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> ProjectTasksFragment.newInstance(projectId)
                1 -> ProjectPhotosFragment()
                2 -> ProjectFilesFragment()
                3 -> ProjectNotesFragment()
                else -> throw Throwable("Invalid position $position")
            }
        }
    }

}

//private lateinit var pieChart: CustomPieChart

// Get the pie chart view
//        val pieChartView = view.findViewById(R.id.pie_chart) as PieChart
// build the pie chart
//        pieChart = CustomPieChart(requireContext(), pieChartView)
