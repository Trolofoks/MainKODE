package com.honey.mainkode.ui.fragments.details.helper

import android.content.Context
import android.icu.text.DateFormatSymbols
import com.honey.mainkode.R
import java.time.LocalDate
import java.time.Period
import java.util.*

class FormatHelper(
    private val context: Context?
) {
    fun formatDate(dateString: String): String {
        val date = LocalDate.parse(dateString)
        val month = date.monthValue
        val monthName = DateFormatSymbols(Locale("ru")).months[month - 1]
        return "${date.dayOfMonth} $monthName ${date.year}"
    }

    fun formatPhoneNumber(phoneNumber: String): String {
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