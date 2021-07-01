package com.example.materialestimator.views

import  android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.materialestimator.R
import com.example.materialestimator.TAG
import com.example.materialestimator.adapters.TaskViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class TaskFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_task, container, false)
        val viewPager = view.findViewById<ViewPager2>(R.id.task_view_pager)
        val tabLayout = view.findViewById<TabLayout>(R.id.tab_layout)
        val backBtn = view.findViewById<Button>(R.id.back_btn)
        val nextBtn = view.findViewById<Button>(R.id.next_btn)

        viewPager.adapter = TaskViewPagerAdapter(this)
        val itemCount = viewPager.adapter!!.itemCount.minus(1)
        TabLayoutMediator(tabLayout, viewPager) { _, _ -> }.attach()
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                when(position){
                    0 -> backBtn.text = "Exit".also {nextBtn.text = "Next"}
                    1 -> backBtn.text = "Back".also {nextBtn.text = "Next"}
                    2 -> backBtn.text = "Back".also {nextBtn.text = "Save"}
                }
            }
        })

        nextBtn.setOnClickListener {
            if (viewPager.currentItem == itemCount){
                Log.i(TAG, "Save")
            }
            if (viewPager.currentItem < itemCount) {
                viewPager.currentItem += 1
            }
        }

        backBtn.setOnClickListener {
            if (viewPager.currentItem == 0){
                Log.i(TAG, "Exit")
            }
            if (viewPager.currentItem <= itemCount){
                viewPager.currentItem -= 1
            }
        }

        return view
    }
}