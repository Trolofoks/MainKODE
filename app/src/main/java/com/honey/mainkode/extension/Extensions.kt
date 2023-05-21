package com.honey.mainkode.extension

import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.support.annotation.StringRes
import android.view.View
import android.view.WindowInsets
import com.google.android.material.tabs.TabLayout
import com.honey.mainkode.R
import com.honey.model.Department
import java.io.Serializable


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
    Pair(Department.Design,  R.string.design),
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

inline fun <reified T : Serializable> Bundle.customGetSerializable(key: String): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getSerializable(key, T::class.java)
    } else {
        @Suppress("DEPRECATION")
        getSerializable(key) as? T
    }
}

fun Dialog.hideSystemUI() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        window?.insetsController?.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
    } else {
        @Suppress("DEPRECATION")
        window?.decorView?.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                )
    }
}
