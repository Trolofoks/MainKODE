package com.honey.mainkode.ui.fragments.details

import android.annotation.SuppressLint
import android.graphics.Color
import android.icu.text.DateFormatSymbols
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import com.honey.mainkode.R
import com.honey.mainkode.base.BaseFragment
import com.honey.mainkode.databinding.FragmentDetailsBinding
import com.honey.mainkode.extension.customGetSerializable
import com.honey.mainkode.extension.stringRes
import com.honey.mainkode.model.Constance
import com.honey.mainkode.model.People
import com.squareup.picasso.Picasso
import java.io.Serializable
import java.time.LocalDate
import java.time.Period
import java.util.*

class DetailsFragment : BaseFragment<FragmentDetailsBinding, DetailsViewModel>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val people = arguments?.customGetSerializable<People>(Constance.DATA_TRANSFER_KEY)
        setStatusBarColor(R.color.white_little_gray)

        people?.let {
            binding.apply {
                val name = people.firstName + " " + people.lastName
                textNameBoth.text = name
                textPeopleTag.text = people.userTag.lowercase()
                if (people.avatarURL.isNotEmpty()) Picasso.get().load(people.avatarURL).into(imagePhoto)
                textDob.text = formatDate(people.dob)
                textNumber.text = formatPhoneNumber(people.phone)
                people.department.stringRes()?.let { textDepartment.setText(it) }
                textYearsOld.text = calculateAge(people.dob)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        setStatusBarColor(R.color.white)
    }

    fun setStatusBarColor(colorId: Int){
        val window: Window = requireActivity().window
        window.statusBarColor = context?.getColor(colorId) ?: Color.WHITE
    }


    private fun formatDate(dateString: String): String {
        val date = LocalDate.parse(dateString)
        val month = date.monthValue
        val monthName = DateFormatSymbols(Locale("ru")).months[month - 1]
        return "${date.dayOfMonth} $monthName ${date.year}"
    }

    private fun formatPhoneNumber(phoneNumber: String): String {
        val digits = phoneNumber.filter { it.isDigit() }
        val countryCode = "+7"
        val areaCode = digits.substring(0, 3)
        val firstPart = digits.substring(3, 6)
        val secondPart = digits.substring(6, 8)
        val thirdPart = digits.substring(8, 10)
        return "$countryCode ($areaCode) $firstPart $secondPart $thirdPart"
    }

    fun calculateAge(dob: String): String {
        val birthDate = LocalDate.parse(dob)
        val currentDate = LocalDate.now()
        val years = Period.between(birthDate, currentDate).years
        return when {
            years % 10 == 0 || years % 10 >= 5 || (years % 100 in 11..14) -> "$years ${context?.getString(R.string.year1)}"
            years % 10 == 1 -> "$years ${context?.getString(R.string.year2)}"
            else -> "$years ${context?.getString(R.string.year3)}"
        }
    }
}

