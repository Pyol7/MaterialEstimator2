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
import com.example.materialestimator.viewModels.SharedViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

/**
 * Sets up the viewPager and controls navigation between child fragments.
 */
class TaskFragment : Fragment(R.layout.fragment_task) {
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var addFab: FloatingActionButton

    /**
     * This function is called first before any other function in the viewPager hierarchy.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Toolbar
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar_main)
        toolbar.setNavigationOnClickListener { findNavController().navigateUp() }
        toolbar.title = "Task ${sharedViewModel.selectedTask.ID}"
        toolbar.subtitle = sharedViewModel.selectedProject.name
        // Options menu
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
        addFab = view.findViewById(R.id.task_add_fab)
        // ViewPager
        viewPager = view.findViewById(R.id.task_viewpager)
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

//            override fun onPageScrollStateChanged(state: Int) {
//                when (state) {
//                    ViewPager2.SCROLL_STATE_SETTLING -> addFab.show()
//                    ViewPager2.SCROLL_STATE_IDLE -> addFab.show()
//                    else -> addFab.hide()
//                }
//            }
            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> {
                        addFab.hide()
                    }
                    1 -> {
                        addFab.show()
                        addFab.setOnClickListener {
                            // Add Material
                            findNavController().navigate(R.id.action_global_materialsFragment)
                        }
                    }
                    2 -> {
                        addFab.show()
                        addFab.setOnClickListener {
                            Log.i(TAG, "Adding Labour...")
                        }
                    }
                    3 -> {
                        addFab.show()
                        addFab.setOnClickListener {
                            Log.i(TAG, "Adding Tool...")
                        }
                    }
                }
            }
        })
        // TabLayout
        tabLayout = view.findViewById(R.id.task_viewpager_tab_layout)
        viewPager.adapter = TaskViewPagerAdapter(this)
        TabLayoutMediator(tabLayout, viewPager)
        { tab, position ->
            when (position) {
                0 -> {
                    tab.id = 0
                    tab.text = "Description"
                }
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
            1 -> TaskMaterialsFragment()
            2 -> TaskLabourFragment()
            3 -> TaskToolsFragment()
            else -> throw Throwable("Invalid position $position")
        }
    }
}