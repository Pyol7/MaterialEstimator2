package com.example.materialestimator.views.project

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.materialestimator.R

class ProjectPhotosFragment : Fragment(R.layout.fragment_project_photos) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btn = view.findViewById(R.id.btn) as Button
        btn.setOnClickListener{
//            findNavController().navigate(R.id.action_projectFragment_to_projectFilesFragment)
        }
    }

}
