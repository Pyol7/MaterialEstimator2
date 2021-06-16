package com.example.materialestimator.views

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.materialestimator.R
import com.example.materialestimator.TAG
import com.example.materialestimator.storage.local.AppDatabase
import com.example.materialestimator.viewModels.MaterialViewModel
import kotlinx.coroutines.CoroutineScope

class HomeFragment : Fragment(R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val materialsBtn = view.findViewById(R.id.materials_btn) as Button
        materialsBtn.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToMaterialListFragment()
            findNavController().navigate(action)
        }
    }
}
