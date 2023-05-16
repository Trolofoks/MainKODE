package com.honey.model

sealed class SortBy {
    object Alphabet : SortBy()
    object Dob: SortBy()
}