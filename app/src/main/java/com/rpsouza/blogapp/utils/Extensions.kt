package com.rpsouza.blogapp.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.rpsouza.blogapp.R

fun Fragment.initToolbar(
    toolbar: Toolbar,
    showIconNavigation: Boolean = true,
) {
    (activity as AppCompatActivity).setSupportActionBar(toolbar)
    (activity as AppCompatActivity).title = ""

    val iconBack = R.drawable.ic_back

    if (showIconNavigation) {
        (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(iconBack)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    toolbar.setNavigationOnClickListener {
        activity?.onBackPressedDispatcher?.onBackPressed()
    }
}