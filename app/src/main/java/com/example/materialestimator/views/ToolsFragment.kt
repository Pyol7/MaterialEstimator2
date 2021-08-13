package com.example.materialestimator.views

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.materialestimator.R
import com.example.materialestimator.adapters.EmployeesFragmentRVAdapter
import com.example.materialestimator.adapters.ToolsFragmentRVAdapter
import com.example.materialestimator.viewModels.ToolsViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ToolsFragment : Fragment(R.layout.fragment_tools),
    ToolsFragmentRVAdapter.OnItemClickListener {
    private val toolsVm: ToolsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Views
        val toolbar = view.findViewById(R.id.tools_toolbar) as Toolbar
        val rv = view.findViewById(R.id.tools_rv) as RecyclerView
        val fab = view.findViewById(R.id.tools_add_fab) as FloatingActionButton
        // Toolbar
        toolbar.setNavigationOnClickListener { findNavController().navigateUp() }
        toolbar.title = "Tools"
        // Adapter
        val rvAdapter = ToolsFragmentRVAdapter()
        rvAdapter.setOnItemClickListener(this@ToolsFragment)
        // RecyclerView
        rv.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = rvAdapter
        }
        toolsVm.getAll().observe(viewLifecycleOwner){
            rvAdapter.setTools(it)
        }

        fab.setOnClickListener{
            // Create new tool
        }
    }

    override fun onItemSelected(id: Long) {
        arguments = bundleOf("key" to id)
        findNavController().navigate(R.id.action_toolsFragment_to_toolFragment, arguments)
    }
}