package com.example.materialestimator.views

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.materialestimator.R
import com.example.materialestimator.viewModels.ToolsViewModel

class ToolFragment : Fragment(R.layout.fragment_tool) {
    private val toolsVM: ToolsViewModel by viewModels()
    private var toolId = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // If there is a saved id then restore it, else use the one that was passed in.
        toolId = savedInstanceState?.getLong("key") ?: arguments?.getLong("key")!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Toolbar
        val toolbar = view.findViewById(R.id.tool_toolbar) as Toolbar
        toolbar.setNavigationOnClickListener { findNavController().navigateUp() }
        toolbar.title = "Tool"
        // Get the tool
        toolsVM.get(toolId).observe(viewLifecycleOwner) { tool ->
            // Set data to views
            val imageIv = view.findViewById(R.id.tool_image_iv) as ImageView
            val imgId = resources.getIdentifier(
                tool?.image,
                "drawable",
                context?.packageName
            )
            imageIv.setImageResource(imgId)
            val typeET = view.findViewById(R.id.tool_type_et) as EditText
            typeET.setText(tool?.type)
            val makeET = view.findViewById(R.id.tool_make_et) as EditText
            makeET.setText(tool?.make)
            val assignedToTV = view.findViewById(R.id.tool_assigned_to_tv) as TextView
//        if (tool.assignedTo != null) {
//            assignedToTV.text = tool.assignedTo!!.name
//        }
        }

        val assignToBtn = view.findViewById(R.id.tool_assigned_to_btn) as ImageButton
        assignToBtn.setOnClickListener {
            findNavController().navigate(R.id.action_global_employeesFragment)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong("key", toolId)
    }

}