package com.example.materialestimator.views

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.materialestimator.R
import com.example.materialestimator.adapters.EmployeesFragmentRVAdapter
import com.example.materialestimator.storage.local.entities.Employee
import com.example.materialestimator.viewModels.EmployeesViewModel
import com.example.materialestimator.viewModels.SharedViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class EmployeesFragment : Fragment(R.layout.fragment_employees),
    EmployeesFragmentRVAdapter.OnItemClickListener {
    private val employeesVm: EmployeesViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Views
        val toolbar = view.findViewById(R.id.employee_toolbar) as Toolbar
        val rv = view.findViewById(R.id.employees_rv) as RecyclerView
        val fab = view.findViewById(R.id.employee_add_fab) as FloatingActionButton
        // Toolbar
        toolbar.setNavigationOnClickListener { findNavController().navigateUp() }
        toolbar.title = "Employees"
        // Adapter
        val rvAdapter = EmployeesFragmentRVAdapter()
        rvAdapter.setOnItemClickListener(this@EmployeesFragment)
        // RecyclerView
        rv.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = rvAdapter
        }
        employeesVm.getAll().observe(viewLifecycleOwner){
            rvAdapter.setEmployees(it)
        }

        fab.setOnClickListener{
            // Create new employee
        }
    }

    override fun onItemSelected(id: Long) {
        arguments = bundleOf("key" to id)
        findNavController().navigate(R.id.action_employeesFragment_to_employeeFragment, arguments)
    }
}