package com.example.materialestimator.utilities

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

class Functions {

    companion object {

        /**
         * Ways to gt the window token.
         * view = getView().getRootView().getWindowToken
         * view = fragment.getView().getRootView().getWindowToken
         * view = view.findViewById(android.R.id.content).getRootView().getWindowToken
         */
        @JvmStatic
        fun hideKeyboard(context: Context, view: View) {
            val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}