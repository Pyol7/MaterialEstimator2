package com.example.materialestimator.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.materialestimator.views.TaskDescriptionFragment
import com.example.materialestimator.views.TaskLabourFragment
import com.example.materialestimator.views.TaskMaterialFragment

/**
 * @param fragment the container fragment that holds the ViewPager2 view
 */
class TaskViewPagerAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> TaskDescriptionFragment.getInstance(position)
            1 -> TaskMaterialFragment.getInstance(position)
            2 -> TaskLabourFragment.getInstance(position)
            else -> throw Throwable("Invalid position $position")
        }
    }

}