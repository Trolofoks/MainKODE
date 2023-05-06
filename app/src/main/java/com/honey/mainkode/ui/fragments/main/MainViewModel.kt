package com.honey.mainkode.ui.fragments.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.honey.mainkode.model.Department
import com.honey.mainkode.model.People
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@HiltViewModel
class MainViewModel : ViewModel() {
    //ofc MVI should looks better
    private val _searchFieldState = MutableStateFlow<String>("")
    val searchFieldState : StateFlow<String> = _searchFieldState.asStateFlow()

    private val _tabState = MutableStateFlow<Department>(Department.Hr)
    val tabState : StateFlow<Department> = _tabState.asStateFlow()

    private val _personsToShowState = MutableStateFlow<List<People>>(emptyList())
    val personsToShowState : StateFlow<List<People>> = _personsToShowState.asStateFlow()

    private val _errorShowShar = MutableSharedFlow<Boolean>()
    val errorShowShar : SharedFlow<Boolean> = _errorShowShar.asSharedFlow()

    private val _screenActive = MutableStateFlow<Boolean>(false)

    init {
        loadData()
    }

    fun updateSearchField(value: String){
        _searchFieldState.value = value
    }

    fun selectTab(department: Department){
        _tabState.value = department
    }

    fun setActiveScreen(boolean: Boolean){
        _screenActive.value = boolean
    }


    //fake API call
    private fun loadData(){
        viewModelScope.launch {
            _screenActive.collect{active->
                //after add pull-to-refresh can change this to if TODO
                while (personsToShowState.value.isEmpty() && active){
                    delay(3000)
                    _personsToShowState.value = listOf(
                        People(
                            firstName = "Dee",
                            lastName = "Reichert",
                            userTag = "LK",
                            department = Department.BackOffice,
                            position = "Technician",
                            dob = "2004-08-02",
                            phone = "802-623-1785"
                        ),
                        People(
                            firstName = "Dee",
                            lastName = "Reichert",
                            userTag = "LK",
                            department = Department.BackOffice,
                            position = "Technician",
                            dob = "2004-08-02",
                            phone = "802-623-1785"
                        ),
                        People(
                            firstName = "Dee",
                            lastName = "Reichert",
                            userTag = "LK",
                            department = Department.BackOffice,
                            position = "Technician",
                            dob = "2004-08-02",
                            phone = "802-623-1785"
                        ),
                        People(
                            firstName = "Dee",
                            lastName = "Reichert",
                            userTag = "LK",
                            department = Department.BackOffice,
                            position = "Technician",
                            dob = "2004-08-02",
                            phone = "802-623-1785"
                        ),
                        People(
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
                    if (_personsToShowState.value.isEmpty()){
                        _errorShowShar.emit(true)
                    }
                }
            }
        }
    }
}