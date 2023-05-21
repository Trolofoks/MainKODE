package com.honey.mainkode.ui.fragments.details

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.honey.mainkode.R
import com.honey.mainkode.base.BaseFragment
import com.honey.mainkode.databinding.FragmentDetailsBinding
import com.honey.mainkode.extension.customGetSerializable
import com.honey.mainkode.extension.stringRes
import com.honey.mainkode.model.Constance
import com.honey.mainkode.ui.fragments.details.helper.FormatHelper
import com.honey.mainkode.ui.fragments.details.helper.ToolbarSetupHelper
import com.honey.model.People
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailsFragment : BaseFragment<FragmentDetailsBinding, DetailsViewModel>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val people = arguments?.customGetSerializable<People>(Constance.DATA_TRANSFER_KEY)
        people?.let{viewModel.setPeople(people)}
        setStatusBarColor(R.color.white_little_gray)

        ToolbarSetupHelper(activity = activity as AppCompatActivity, binding).setupToolbar()
        val formatHelper = FormatHelper(context)

        lifecycleScope.launch{
            viewModel.peopleToShowState.collect(){people->
                people?.let {
                    binding.apply {
                        val name = people.firstName + " " + people.lastName
                        textNameBoth.text = name
                        textPeopleTag.text = people.userTag.lowercase()
                        if (people.avatarURL.isNotEmpty()) Picasso.get().load(people.avatarURL).into(imagePhoto)
                        textDob.text = formatHelper.formatDate(people.dob)
                        textNumber.text = formatHelper.formatPhoneNumber(people.phone)
                        people.department.stringRes()?.let { textDepartment.setText(it) }
                        textYearsOld.text = formatHelper.calculateAge(people.dob)
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        setStatusBarColor(R.color.white)
    }

    private fun setStatusBarColor(colorId: Int){
        val window: Window = requireActivity().window
        window.statusBarColor = context?.getColor(colorId) ?: Color.WHITE
    }



}

