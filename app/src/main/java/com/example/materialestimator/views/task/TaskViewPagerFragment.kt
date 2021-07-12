package com.example.materialestimator.views.task

import  android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.materialestimator.R
import com.example.materialestimator.TAG
import com.example.materialestimator.viewModels.TaskViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

/**
 * Responsible for setting up the viewPager, controlling navigation between viewPager child
 * fragments and setting the Task on the VM so that child fragments can access it.
 */
class TaskViewPagerFragment : Fragment() {
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_task_view_pager, container, false)
        val toolbar = view.findViewById(R.id.task_toolbar) as Toolbar
        toolbar.inflateMenu(R.menu.general_toolbar_menu)
        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        viewPager = view.findViewById(R.id.task_view_pager)
        tabLayout = view.findViewById(R.id.tab_layout)
        return view
    }

    /**
     * This function is called first before any other function in the viewPager hierarchy.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Connect viewPager with adapter
        viewPager.adapter = TaskViewPagerAdapter(this)
        // Connect tabLayout
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
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
            3 -> TaskLabourFragment()
            else -> throw Throwable("Invalid position $position")
        }
    }
}