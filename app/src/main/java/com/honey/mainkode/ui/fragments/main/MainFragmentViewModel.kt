package com.honey.mainkode.ui.fragments.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.honey.mainkode.model.Department
import com.honey.mainkode.model.Person
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainFragmentViewModel @Inject constructor(
    private val loadAppPersonsUseCase: () -> Unit
) : ViewModel() {

    private var persons = MutableStateFlow<List<Person>?>(null)
    init {
        Log.d("MyLog","Just Test for Recreating")
        loadData()
        subscribeToPersons()
    }

    //ofc MVI should looks better
    private val _searchFieldState = MutableStateFlow<String>("")
    val searchFieldState : StateFlow<String> = _searchFieldState.asStateFlow()

    private val _tabState = MutableStateFlow<Department>(Department.Hr)
    val tabState : StateFlow<Department> = _tabState.asStateFlow()

    private val _personsToShowState = MutableStateFlow<List<Person>?>(null)
    val personsToShowState : StateFlow<List<Person>?> = _personsToShowState.asStateFlow()

    private val _skeletonShowState = MutableStateFlow<Boolean>(true)
    val skeletonShowState : StateFlow<Boolean> = _skeletonShowState.asStateFlow()

    private val _errorShowState = MutableSharedFlow<Boolean>()
    val errorShowState : SharedFlow<Boolean> = _errorShowState.asSharedFlow()

    fun updateSearchField(value: String){
        _searchFieldState.value = value
    }

    fun selectTab(department: Department){
        _tabState.value = department

    }


    //fake API call
    private fun loadData(){
        viewModelScope.launch {
            //for future
            loadAppPersonsUseCase

            persons.value = listOf(
                Person(
                    firstName = "Dee",
                    lastName = "Reichert",
                    userTag = "LK",
                    department = Department.BackOffice,
                    position = "Technician",
                    dob = "2004-08-02",
                    phone = "802-623-1785"
                ),
                Person(
                    firstName = "Dee",
                    lastName = "Reichert",
                    userTag = "LK",
                    department = Department.BackOffice,
                    position = "Technician",
                    dob = "2004-08-02",
                    phone = "802-623-1785"
                ),
                Person(
                    firstName = "Dee",
                    lastName = "Reichert",
                    userTag = "LK",
                    department = Department.BackOffice,
                    position = "Technician",
                    dob = "2004-08-02",
                    phone = "802-623-1785"
                ),
                Person(
                    firstName = "Dee",
                    lastName = "Reichert",
                    userTag = "LK",
                    department = Department.BackOffice,
                    position = "Technician",
                    dob = "2004-08-02",
                    phone = "802-623-1785"
                ),
                Person(
                    firstName = "Dee",
                    lastName = "Reichert",
                    userTag = "LK",
                    department = Department.BackOffice,
                    position = "Technician",
                    dob = "2004-08-02",
                    phone = "802-623-1785"
                )
            )
            delay(10000)
            if (persons == null){
                //trigger for navigation to error screen
                _errorShowState.tryEmit(true)
            }
        }
    }

    private fun subscribeToPersons() {
        viewModelScope.launch {
            persons.collect(){list->
                if (list != null){
                    _skeletonShowState.value = false
                    _personsToShowState.value = list
                }
            }
        }
    }

     val a = Department
}