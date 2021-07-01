package com.example.materialestimator.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.materialestimator.R

class TaskLabourFragment: Fragment() {

    companion object {
        private const val ARG_POSITION = "position"

        fun getInstance(position: Int): Fragment {
            val taskLabourFragment = TaskLabourFragment()
            val bundle = Bundle()
            bundle.putInt(ARG_POSITION, position)
            taskLabourFragment.arguments = bundle
            return taskLabourFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_task_labour, container, false)


        return view
    }
}
