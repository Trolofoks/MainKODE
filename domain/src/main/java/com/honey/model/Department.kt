package com.honey.model

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
}
