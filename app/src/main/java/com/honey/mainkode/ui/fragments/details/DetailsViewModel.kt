package com.honey.mainkode.ui.fragments.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.honey.model.People
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor() : ViewModel(){

    private val _peopleToShowState = MutableStateFlow<People?>(null)
    val peopleToShowState : StateFlow<People?> = _peopleToShowState.asStateFlow()

    private val _navigateToMainShar = MutableSharedFlow<Boolean>()
    val navigateToMainShar : SharedFlow<Boolean> = _navigateToMainShar.asSharedFlow()

    fun setPeople(people: People){
        _peopleToShowState.value = people
    }

    fun retryButtonClicked(){
        viewModelScope.launch {
            _navigateToMainShar.emit(true)
        }
    }
}