package com.example.materialestimator.viewModels

import android.app.Application
import androidx.lifecycle.*
import com.example.materialestimator.storage.local.entities.Employee
import com.example.materialestimator.storage.local.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EmployeesViewModel(application: Application) : AndroidViewModel(application) {
    private val employeeDao = AppDatabase.getInstance(application).employeeDao()

    fun get(ID: Long?): LiveData<Employee> {
        return employeeDao.get(ID)
    }

    fun getAll(): LiveData<List<Employee>> {
        return employeeDao.getAll()
    }


    fun insert(employee: Employee?) {
        viewModelScope.launch(Dispatchers.IO) {
            employeeDao.insert(employee)
        }
    }

    fun update(employee: Employee?) {
        viewModelScope.launch(Dispatchers.IO) {
            employeeDao.update(employee)
        }
    }

    fun updateAll(employees: List<Employee>) {
        viewModelScope.launch(Dispatchers.IO) {
            employeeDao.updateAll(employees)
        }
    }

    fun clear() {
        viewModelScope.launch(Dispatchers.IO) {
            employeeDao.clear()
        }
    }
}
