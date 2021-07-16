package com.example.materialestimator.views.task

import  android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.materialestimator.R
import com.example.materialestimator.TAG
import com.example.materialestimator.models.entities.Task
import com.example.materialestimator.viewModels.SharedViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

/**
 * Sets up the viewPager and controls navigation between child fragments.
 */
class TaskViewPagerFragment : Fragment(R.layout.fragment_task_view_pager) {
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    /**
     * This function is called first before any other function in the viewPager hierarchy.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar = view.findViewById<Toolbar>(R.id.task_viewpager_toolbar)
        toolbar.title = "Task ${sharedViewModel.selectedTask.ID}"
        toolbar.subtitle = sharedViewModel.selectedProject.name
        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        toolbar.inflateMenu(R.menu.add_help_menu)
        val addBtn = toolbar.menu.findItem(R.id.action_add)
        viewPager = view.findViewById(R.id.task_viewpager)
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                if (position == 0) {
                    addBtn.isVisible = false
                    toolbar.setOnMenuItemClickListener {
                        when (it.itemId) {
                            R.id.action_help -> {
                                Log.i(TAG, "Help...")
                                true
                            }
                            else -> false
                        }
                    }
                }
                if (position == 1) {
                    addBtn.isVisible = true
                    toolbar.setOnMenuItemClickListener {
                        when (it.itemId) {
                            R.id.action_add -> {
                                // Add Material
                                findNavController().navigate(R.id.action_global_materialsFragment)
                                true
                            }
                            R.id.action_help -> {
                                Log.i(TAG, "Help...")
                                true
                            }
                            else -> false
                        }
                    }
                }
                if (position == 2) {
                    addBtn.isVisible = true
                    toolbar.setOnMenuItemClickListener {
                        when (it.itemId) {
                            R.id.action_add -> {
                                Log.i(TAG, "Add Employee...")
                                true
                            }
                            R.id.action_help -> {
                                Log.i(TAG, "Help...")
                                true
                            }
                            else -> false
                        }
                    }
                }
                if (position == 3) {
                    addBtn.isVisible = true
                    toolbar.setOnMenuItemClickListener {
                        when (it.itemId) {
                            R.id.action_add -> {
                                Log.i(TAG, "Add Tool...")
                                true
                            }
                            R.id.action_help -> {
                                Log.i(TAG, "Help...")
                                true
                            }
                            else -> false
                        }
                    }
                }

            }
        })

        tabLayout = view.findViewById(R.id.tab_viewpager_layout)
        // Connect viewPager with adapter
        viewPager.adapter = TaskViewPagerAdapter(this)
        // Connect tabLayout
        TabLayoutMediator(tabLayout, viewPager)
        { tab, position ->
            when (position) {
                0 -> tab.text = "Description"
                1 -> tab.text = "Material"
                2 -> tab.text = "Labour"
                3 -> tab.text = "Tools"
            }
        }.attach()

    }
}

/**
 * @param fragment the container fragment that holds the ViewPager2 view
 */
private class TaskViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> TaskDescriptionFragment()
            1 -> TaskMaterialFragment()
            2 -> TaskLabourFragment()
            3 -> TaskToolFragment()
            else -> throw Throwable("Invalid position $position")
        }
    }
}