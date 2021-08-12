package com.example.materialestimator.views

import android.os.Bundle
import android.view.View
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

class EmployeesFragment : Fragment(R.layout.fragment_employees),
    EmployeesFragmentRVAdapter.OnItemClickListener {
    private val sharedVm: SharedViewModel by activityViewModels()
    private val employeesVm: EmployeesViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Adapter
        val rvAdapter = EmployeesFragmentRVAdapter()
        rvAdapter.setOnItemClickListener(this@EmployeesFragment)
        // RV
        val rv = view.findViewById(R.id.employees_rv) as RecyclerView
        rv.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = rvAdapter
        }
        employeesVm.getAll().observe(viewLifecycleOwner){
            rvAdapter.setEmployees(it)
        }
    }

    override fun onItemSelected(employee: Employee) {
//        sharedVm.selectedTool.assignedTo = employee
        findNavController().navigateUp()
    }
}