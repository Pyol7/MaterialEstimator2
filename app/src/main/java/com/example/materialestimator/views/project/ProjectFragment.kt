package com.example.materialestimator.views.project

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.materialestimator.R
import com.example.materialestimator.TAG
import com.example.materialestimator.models.entities.Project
import com.example.materialestimator.viewModels.SharedViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class ProjectFragment : Fragment(R.layout.fragment_project) {
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var addFab: FloatingActionButton
    private lateinit var project: Project

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Get the selected project
        project = sharedViewModel.selectedProject
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Toolbar
        val toolbar = view.findViewById(R.id.project_toolbar) as Toolbar
        toolbar.setNavigationOnClickListener { findNavController().navigateUp() }
        toolbar.title = project.name
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
        // Project image
        val projectMainImageIV = view.findViewById(R.id.project_main_image_iv) as ImageView
        val imgId = context?.resources?.getIdentifier(
            project.image,
            "drawable",
            requireContext().packageName
        ) as Int
        projectMainImageIV.setImageResource(imgId)
        // fab
        addFab = view.findViewById(R.id.project_add_fab)
        // ViewPager
        viewPager = view.findViewById(R.id.project_viewpager)
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
                            Log.i(TAG, "Adding task...")
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
        // TabLayout
        tabLayout = view.findViewById(R.id.project_viewpager_tab_layout)
        viewPager.adapter = ProjectViewPagerAdapter(this)
        TabLayoutMediator(tabLayout, viewPager)
        { tab, position ->
            when (position) {
                0 -> tab.text = "Tasks"
                1 -> tab.text = "Photos"
                2 -> tab.text = "Files"
                3 -> tab.text = "Notes"
            }
        }.attach()
    }
}
/**
 * @param fragment the container fragment that holds the ViewPager2 view
 */
private class ProjectViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return 4
    }
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ProjectTasksFragment()
            1 -> ProjectPhotosFragment()
            2 -> ProjectFilesFragment()
            3 -> ProjectNotesFragment()
            else -> throw Throwable("Invalid position $position")
        }
    }
}

//private lateinit var pieChart: CustomPieChart

// Get the pie chart view
//        val pieChartView = view.findViewById(R.id.pie_chart) as PieChart
// build the pie chart
//        pieChart = CustomPieChart(requireContext(), pieChartView)
