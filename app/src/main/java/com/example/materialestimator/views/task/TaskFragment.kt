package com.example.materialestimator.views.task

import  android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.materialestimator.R
import com.example.materialestimator.TAG
import com.example.materialestimator.storage.local.entities.Task
import com.example.materialestimator.utilities.MoshiConverters
import com.example.materialestimator.viewModels.TasksViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

/**
 * Sets up the view pager and tabs.
 * Each tab displays a fragment that displays data associated with the task.
 */

class TaskFragment : Fragment(R.layout.fragment_task) {
    private val tasksVM: TasksViewModel by viewModels()
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var addFab: FloatingActionButton
    private lateinit var task: Task
    private var taskId = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // If there is a saved id then restore it, else use the one that was passed in.
        taskId = savedInstanceState?.getLong("key") ?: arguments?.getLong("key")!!

        /**
         * Listens for the result from MaterialsFragment
         */
        setFragmentResultListener("materialsFragmentRequestKey") { _, bundle ->
            // Get result
            val newMaterials = MoshiConverters.jsonToMaterials(bundle.getString("key"))
            if (this::task.isInitialized) {
                // Add material to task.materials
                task.addMaterials(newMaterials)
                // Update task in db
                tasksVM.update(task)
            }
        }
    }

    /**
     * This function is called first before any other function in the viewPager hierarchy.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Views
        val toolbar = view.findViewById(R.id.toolbar_main) as Toolbar
        addFab = view.findViewById(R.id.task_add_fab)
        viewPager = view.findViewById(R.id.task_viewpager)
        tabLayout = view.findViewById(R.id.task_viewpager_tab_layout)

        // Toolbar
        toolbar.setNavigationOnClickListener { findNavController().navigateUp() }
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
        // Get task
        tasksVM.get(taskId).observe(viewLifecycleOwner) {
            task = it
            toolbar.title = "Task ${task.taskId}"
        }
        // ViewPager
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            var currentPage = 0
            override fun onPageScrollStateChanged(state: Int) {
                if (currentPage != 0) {
                    when (state) {
                        ViewPager2.SCROLL_STATE_SETTLING -> addFab.show()
                        ViewPager2.SCROLL_STATE_IDLE -> addFab.show()
                        else -> addFab.hide()
                    }
                }
            }

            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> {
                        currentPage = 0
                        addFab.hide()
                    }
                    1 -> {
                        currentPage = 1
                        addFab.setOnClickListener {
                            // Show MaterialsFragment for selecting materials to add
                            findNavController().navigate(R.id.action_global_materialsFragment)
                        }
                    }
                    2 -> {
                        currentPage = 2
                        addFab.setOnClickListener {
                            // Show EmployeesFragment for selecting employees to add
                            findNavController().navigate(R.id.action_global_employeesFragment)
                        }
                    }
                    3 -> {
                        currentPage = 3
                        addFab.setOnClickListener {
                            // Show ToolsFragment for selecting tools to add
                            findNavController().navigate(R.id.action_global_toolsFragment)
                        }
                    }
                }
            }
        })
        viewPager.adapter = TaskViewPagerAdapter(this)
        // TabLayout
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong("key", taskId)
    }

    // ViewPagerAdapter @param fragment - the container fragment that holds the ViewPager2 view
    private inner class TaskViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int {
            return 4
        }

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> TaskDescriptionFragment.newInstance(taskId)
                1 -> TaskMaterialsFragment.newInstance(taskId)
                2 -> TaskEmployeesFragment.newInstance(taskId)
                3 -> TaskToolsFragment.newInstance(taskId)
                else -> throw Throwable("Invalid position $position")
            }
        }
    }
}
