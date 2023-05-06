package com.honey.mainkode.ui.fragments.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.material.tabs.TabLayout
import com.honey.mainkode.extension.departmentByPose
import com.honey.mainkode.model.Department
import com.honey.mainkode.model.People
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@HiltViewModel
class MainViewModel : ViewModel() {
    private val allPeoples = MutableStateFlow<List<People>>(emptyList())
    //ofc MVI should looks better
    private val _searchFieldState = MutableStateFlow<String>("")
    val searchFieldState : StateFlow<String> = _searchFieldState.asStateFlow()

    private val _tabPosState = MutableStateFlow<TabLayout.Tab?>(null)
    val tabPosState : StateFlow<TabLayout.Tab?> = _tabPosState.asStateFlow()

    private val _peoplesToShowState = MutableStateFlow<List<People>?>(null)
    val peoplesToShowState : StateFlow<List<People>?> = _peoplesToShowState.asStateFlow()

    private val _errorShowShar = MutableSharedFlow<Boolean>()
    val errorShowShar : SharedFlow<Boolean> = _errorShowShar.asSharedFlow()

    private val screenActive = MutableStateFlow<Boolean>(false)

    init {
        loadData()
    }

    fun setSelectTab(tab: TabLayout.Tab){
        _tabPosState.value = tab
        filterPeoples()
    }

    fun setSearchField(value: String){
        _searchFieldState.value = value
        filterPeoples()
    }

    fun setActiveScreen(boolean: Boolean){
        screenActive.value = boolean
    }



    private fun filterPeoples(){
        val value = searchFieldState.value.lowercase()
        val newList = allPeoples.value.filter { people->
            (people.firstName.lowercase().contains(value) ||
            people.lastName.lowercase().contains(value) ||
            people.userTag.lowercase().contains(value) ||
            people.position.lowercase().contains(value) ||
            people.dob.lowercase().contains(value) ||
            people.phone.lowercase().contains(value)) && (
                people.department == tabPosState.value?.departmentByPose() ||
                tabPosState.value?.departmentByPose() == Department.All
            )
        }
        _peoplesToShowState.value = newList
    }

    //fake API call
    private fun loadData(){
        viewModelScope.launch {
            screenActive.collect{ active->
                //after add pull-to-refresh can change while to if TODO
                while (allPeoples.value.isEmpty() && active){
                    delay(3000)
                    allPeoples.value = listOf(
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
                            userTag = "LN",
                            department = Department.BackOffice,
                            position = "Technician",
                            dob = "2004-08-02",
                            phone = "802-623-1785"
                        ),
                        People(
                            firstName = "Dee",
                            lastName = "Reichert",
                            userTag = "LI",
                            department = Department.BackOffice,
                            position = "Technician",
                            dob = "2004-08-02",
                            phone = "802-623-1785"
                        ),
                        People(
                            firstName = "Dee",
                            lastName = "Reichert",
                            userTag = "KK",
                            department = Department.Support,
                            position = "Technician",
                            dob = "2004-08-02",
                            phone = "802-623-1785"
                        ),
                        People(
                            firstName = "Dee",
                            lastName = "Reichert",
                            userTag = "KI",
                            department = Department.Android,
                            position = "Technician",
                            dob = "2004-08-02",
                            phone = "802-623-1785"
                        )
                    )
                    _peoplesToShowState.value = allPeoples.value
                    delay(10000)
                    if (allPeoples.value.isEmpty()){
                        _errorShowShar.emit(true)
                    }
                }
            }
        }
    }
}