package com.example.materialestimator.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.materialestimator.R

class TaskDescriptionFragment: Fragment() {

    companion object {
        private const val ARG_POSITION = "position"

        fun getInstance(position: Int): Fragment {
            val taskDescriptionFragment = TaskDescriptionFragment()
            val bundle = Bundle()
            bundle.putInt(ARG_POSITION, position)
            taskDescriptionFragment.arguments = bundle
            return taskDescriptionFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_task_description, container, false)


        return view
    }
}
