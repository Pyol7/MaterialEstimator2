package com.example.materialestimator.views.task

import  android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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
    private val vm: TaskViewModel by viewModels()
    private lateinit var pageChangeListener: ViewPager2.OnPageChangeCallback
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var backBtn: Button
    private lateinit var nextBtn: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_task_view_pager, container, false)
        viewPager = view.findViewById(R.id.task_view_pager)
        tabLayout = view.findViewById(R.id.tab_layout)
        nextBtn = view.findViewById(R.id.next_btn)
        backBtn = view.findViewById(R.id.back_btn)
        return view
    }

    /**
     * This function is called first before any other function in the viewPager hierarchy.
     * Therefore this is where the selected Task must be retrieved from the db and stored
     * on the VM (Note the VM is only instantiated when an observer is attached).
     * Once the Task is on the VM it is then available to all child fragments.
     * Viewpager child fragments must instantiate their VM with this fragment's scope so that the
     * VM stays alive for as long as the viewpager stayed alive.
     *
     * At this point we can now attach the viewpager adapter to the viewpager and child fragments
     * safely retrieve the selected Task from the VM.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val taskID = arguments?.getInt("taskID")
        vm.get(taskID).observe(viewLifecycleOwner) {
            Log.i(TAG, "TaskViewPagerFragment: set selectedTask = $it")
            // Set selected Task to VM
            vm.selectedTask.value = it
            // Connect viewPager with adapter
            viewPager.adapter = TaskViewPagerAdapter(this) //3
            // Connect tabLayout
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            }.attach()
            // Register page change listener
            pageChangeListener  = object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    when (position) {
                        0 -> backBtn.text = "Exit".also { nextBtn.text = "Next" }
                        1 -> backBtn.text = "Back".also { nextBtn.text = "Next" }
                        2 -> backBtn.text = "Back".also { nextBtn.text = "Save" }
                    }
                }
            }
            viewPager.registerOnPageChangeCallback(pageChangeListener)
            // Setup button navigation
            val itemCount = viewPager.adapter!!.itemCount.minus(1)
            nextBtn.setOnClickListener {
                if (viewPager.currentItem == itemCount) {
                    Log.i(TAG, "Saving")
                }
                if (viewPager.currentItem < itemCount) {
                    viewPager.currentItem += 1
                }
            }
            backBtn.setOnClickListener {
                if (viewPager.currentItem == 0) {
                    Log.i(TAG, "Exiting")
                    parentFragmentManager.popBackStack()
                }
                if (viewPager.currentItem <= itemCount) {
                    viewPager.currentItem -= 1
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewPager.unregisterOnPageChangeCallback(pageChangeListener)
    }

    /**
     * @param fragment the container fragment that holds the ViewPager2 view
     */
    private class TaskViewPagerAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {

        override fun getItemCount(): Int {
            return 3
        }

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> TaskDescriptionFragment()
                1 -> TaskMaterialFragment()
                2 -> TaskLabourFragment()
                else -> throw Throwable("Invalid position $position")
            }
        }
    }

}