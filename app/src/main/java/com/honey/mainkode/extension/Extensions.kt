package com.honey.mainkode.extension

import android.support.annotation.StringRes
import com.google.android.material.tabs.TabLayout
import com.honey.mainkode.R
import com.honey.mainkode.model.Department


val posList = listOf<Department>(
    //in order similar tabs in XML
    Department.All,
    Department.Android,
    Department.Ios,
    Department.Design,
    Department.Management,
    Department.Qa,
    Department.BackOffice,
    Department.Frontend,
    Department.Hr,
    Department.Pr,
    Department.Backend,
    Department.Support,
    Department.Analytics
)

val mapOfStringRes :Map<Department, Int> = mapOf(
    Pair(Department.Android, R.string.android),
    Pair(Department.Ios, R.string.ios),
    Pair(Department.Design, R.string.design),
    Pair(Department.Management, R.string.management),
    Pair(Department.Qa, R.string.qa),
    Pair(Department.BackOffice, R.string.back_office),
    Pair(Department.Frontend, R.string.frontend),
    Pair(Department.Hr, R.string.hr),
    Pair(Department.Pr, R.string.pr),
    Pair(Department.Backend, R.string.backend),
    Pair(Department.Support, R.string.support),
    Pair(Department.Analytics, R.string.analytics),

    )


fun TabLayout.Tab.departmentByPose(): Department {
    return posList[position]
}

fun Department.stringRes(): Int? {
    return mapOfStringRes[this]
}