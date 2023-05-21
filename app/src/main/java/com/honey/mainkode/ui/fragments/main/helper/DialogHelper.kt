package com.honey.mainkode.ui.fragments.main.helper

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import com.honey.mainkode.R
import com.honey.mainkode.databinding.FragmentMainBinding
import com.honey.mainkode.databinding.PartBottomSheetDialogBinding
import com.honey.mainkode.extension.hideSystemUI
import com.honey.mainkode.ui.fragments.main.MainViewModel
import com.honey.model.SortBy

class DialogHelper(
    private val binding: FragmentMainBinding,
    private val viewModel: MainViewModel,
    private val context: Context
    ) {
    fun showDialog() {
        val dialog = Dialog(context)

        val binding = PartBottomSheetDialogBinding.inflate(LayoutInflater.from(context))
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(binding.root)

        dialog.show()
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog.window?.setGravity(Gravity.BOTTOM)

        dialog.hideSystemUI()

        binding.apply {
            radioGroup.check(
                when (viewModel.sortByState.value) {
                    SortBy.Alphabet -> radioButtonAlphabet.id
                    SortBy.Dob -> radioButtonDob.id
                }
            )

            radioGroup.setOnCheckedChangeListener { _, id ->
                when (id) {
                    radioButtonAlphabet.id -> {
                        viewModel.setSort(SortBy.Alphabet)
                    }
                    radioButtonDob.id -> {
                        viewModel.setSort(SortBy.Dob)
                    }
                }
            }
        }
    }
}