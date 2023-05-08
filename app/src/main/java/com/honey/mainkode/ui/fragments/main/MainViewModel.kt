package com.honey.mainkode.ui.fragments.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.material.tabs.TabLayout
import com.honey.mainkode.extension.departmentByPose
import com.honey.mainkode.model.Department
import com.honey.mainkode.model.People
import com.honey.mainkode.model.SortBy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

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

    private val _sortByState = MutableStateFlow<SortBy>(SortBy.Alphabet)
    val sortByState : StateFlow<SortBy> = _sortByState.asStateFlow()

    private val screenActive = MutableStateFlow<Boolean>(false)

    init {
        loadData()
    }

    fun setSelectTab(tab: TabLayout.Tab){
        _tabPosState.value = tab
        setNewPeoples()
    }

    fun setSearchField(value: String){
        _searchFieldState.value = value
        setNewPeoples()
    }

    fun setActiveScreen(boolean: Boolean){
        screenActive.value = boolean
    }

    fun setSort(sort: SortBy){
        _sortByState.value = sort
        setNewPeoples()
    }

    private fun setNewPeoples(){
        val filtered = filterPeoples(
            peopleList = allPeoples.value,
            searchField = searchFieldState.value,
            department = (tabPosState.value?.departmentByPose())?:Department.All
        )
        val sorted = sortBy(
            peopleList = filtered,
            sort = sortByState.value,
            today = LocalDate.now()
        )
        _peoplesToShowState.value = sorted
    }

    private fun sortBy(
        peopleList: List<People>,
        sort: SortBy,
        today: LocalDate
    ) : List<People>{
        val newList : List<People> = when(sort){
            SortBy.Alphabet -> {
                peopleList.sortedBy { it.firstName }
            }
            SortBy.Dob -> {
                dobSort(peopleList, today)
            }
        }
        return newList
    }


    private fun dobSort(peopleList: List<People>,  today: LocalDate) : List<People>{
        val sortedList = peopleList.sortedWith(compareBy
        { person ->
            val dob = LocalDate.parse(person.dob)
            val nextBirthday = if (dob.withYear(today.year).isBefore(today)) {
                dob.withYear(today.year + 1)
            } else {
                dob.withYear(today.year)
            }
            ChronoUnit.DAYS.between(today, nextBirthday)
        })
        return sortedList
    }

    private fun filterPeoples(
        peopleList : List<People>,
        searchField: String,
        department: Department
    ) : List<People>{
        val value = searchField.lowercase()
        val newList = peopleList.filter { people->
            (people.firstName.lowercase().contains(value) ||
            people.lastName.lowercase().contains(value) ||
            people.userTag.lowercase().contains(value) ||
            people.position.lowercase().contains(value) ||
            people.dob.lowercase().contains(value) ||
            people.phone.lowercase().contains(value)) && (
                people.department == department || department == Department.All
            )
        }
        return newList
    }

    //fake API call
    private fun loadData(){
        viewModelScope.launch {
            screenActive.collect{ active->
                //after add pull-to-refresh can change while to if TODO
                while (allPeoples.value.isEmpty() && active){
                    delay(3000)
                    val peoples  = arrayListOf<People>()
                    for (i in 0..1) {
                        peoples.add(
                            People(
                                firstName = "Bee",
                                lastName = "Reichert" + "$i",
                                userTag = "LK",
                                department = Department.BackOffice,
                                position = "Technician",
                                dob = "2004-01-10",
                                phone = "802-623-1785"
                            )
                        )
                        peoples.add(
                            People(
                                firstName = "Aee",
                                lastName = "Reichert" + "$i",
                                userTag = "LK",
                                department = Department.BackOffice,
                                position = "Technician",
                                dob = "2004-07-29",
                                phone = "802-623-1785"
                            )
                        )
                        peoples.add(
                            People(
                                firstName = "Cee",
                                lastName = "Reichert" + "$i",
                                userTag = "LK",
                                department = Department.BackOffice,
                                position = "Technician",
                                dob = "2004-10-20",
                                phone = "802-623-1785"
                            )
                        )
                    }
                    allPeoples.value = peoples
                    setNewPeoples()
                    delay(10000)
                    if (allPeoples.value.isEmpty()){
                        _errorShowShar.emit(true)
                    }
                }
            }
        }
    }
}