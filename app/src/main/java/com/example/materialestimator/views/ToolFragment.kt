package com.example.materialestimator.views

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.materialestimator.R
import com.example.materialestimator.TAG
import com.example.materialestimator.models.entities.Tool
import com.example.materialestimator.viewModels.SharedViewModel

class ToolFragment : Fragment(R.layout.fragment_tool) {
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var tool:Tool

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tool = sharedViewModel.tool
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Toolbar
        val toolbar = view.findViewById(R.id.tool_toolbar) as Toolbar
        toolbar.setNavigationOnClickListener { findNavController().navigateUp() }
        toolbar.title = "Tool"
        // Set data to views
        val imageIv = view.findViewById(R.id.tool_image_iv) as ImageView
        val imgId = resources.getIdentifier(
            tool.image,
            "drawable",
            context?.packageName
        )
        imageIv.setImageResource(imgId)
        val typeET = view.findViewById(R.id.tool_type_et) as EditText
        typeET.setText(tool.type)
        val makeET = view.findViewById(R.id.tool_make_et) as EditText
        makeET.setText(tool.make)
    }

}