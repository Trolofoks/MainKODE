package com.honey.mainkode.model

import android.support.annotation.StringRes
import com.honey.mainkode.R

sealed class Department {
    object All : Department()
    object Android : Department()
    object Ios: Department()
    object Design: Department()
    object Management: Department()
    object Qa: Department()
    object BackOffice: Department()
    object Frontend : Department()
    object Hr: Department()
    object Pr: Department()
    object Backend: Department()
    object Support: Department()
    object Analytics: Department()
    companion object {
        val posList = listOf<Department>(
            //in order similar tabs in XML
            All,Android,Ios,Design,Management,Qa,BackOffice,Frontend,Hr,Pr,Backend,Support,Analytics
        )
        val map :Map<Department, Int> = mapOf(
            Pair(Android, R.string.android),
            Pair(Ios, R.string.ios),
            Pair(Design, R.string.design),
            Pair(Management, R.string.management),
            Pair(Qa, R.string.qa),
            Pair(BackOffice, R.string.back_office),
            Pair(Frontend, R.string.frontend),
            Pair(Hr, R.string.hr),
            Pair(Pr, R.string.pr),
            Pair(Backend, R.string.backend),
            Pair(Support, R.string.support),
            Pair(Analytics, R.string.analytics),

        )
    }
}
