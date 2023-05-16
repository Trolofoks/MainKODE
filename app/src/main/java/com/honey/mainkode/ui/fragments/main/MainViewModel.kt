package com.honey.mainkode.ui.fragments.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.material.tabs.TabLayout
import com.honey.mainkode.extension.departmentByPose
import com.honey.model.SortBy
import com.honey.model.Department
import com.honey.model.People
import com.honey.usecase.FilterPeoplesUseCase
import com.honey.usecase.SortPeoplesUseCase

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val filterPeoplesUseCase: FilterPeoplesUseCase,
    private val sortPeoplesUseCase: SortPeoplesUseCase
): ViewModel() {

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
        val filtered = filterPeoplesUseCase.invoke(
            peopleList = allPeoples.value,
            searchField = searchFieldState.value,
            department = (tabPosState.value?.departmentByPose())?: Department.All
        )
        val sorted = sortPeoplesUseCase.invoke(
            peopleList = filtered,
            sort = sortByState.value,
            today = LocalDate.now()
        )
        _peoplesToShowState.value = sorted
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