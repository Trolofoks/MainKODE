package com.honey.mainkode.model

sealed class SortBy {
    object Alphabet : SortBy()
    object Dob: SortBy()
}