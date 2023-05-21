package com.honey.mainkode.ui.fragments.details.helper

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.DrawableCompat
import com.honey.mainkode.databinding.FragmentDetailsBinding

class ToolbarSetupHelper(
    private val activity: AppCompatActivity,
    private val binding: FragmentDetailsBinding
) {
    fun setupToolbar(){
        val toolbar = binding.toolbar

        activity.apply {
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = ""

            val drawable = toolbar.navigationIcon?.mutate()
            if (drawable != null) {
                DrawableCompat.setTint(drawable, Color.BLACK)
            }
            toolbar.navigationIcon = drawable
        }
        toolbar.setNavigationOnClickListener {
            activity.onBackPressedDispatcher.onBackPressed()
        }
    }
}