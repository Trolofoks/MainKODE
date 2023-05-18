package com.honey.mainkode.ui.fragments.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.material.tabs.TabLayout
import com.honey.mainkode.extension.departmentByPose
import com.honey.model.SortBy
import com.honey.model.Department
import com.honey.model.People
import com.honey.usecase.FilterPeoplesUseCase
import com.honey.usecase.LoadPeoplesUseCase
import com.honey.usecase.SortPeoplesUseCase

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val filterPeoplesUseCase: FilterPeoplesUseCase,
    private val sortPeoplesUseCase: SortPeoplesUseCase,
    private val loadPeoplesUseCase: LoadPeoplesUseCase
): ViewModel() {
    private val allPeoples = MutableStateFlow<List<People>>(emptyList())
    //ofc MVI should looks better
    private val _searchFieldState = MutableStateFlow<String>("")
    val searchFieldState : StateFlow<String> = _searchFieldState.asStateFlow()

    private val _tabPosState = MutableStateFlow<TabLayout.Tab?>(null)
    val tabPosState : StateFlow<TabLayout.Tab?> = _tabPosState.asStateFlow()

    private val _peoplesToShowState = MutableStateFlow<List<People>>(emptyList())
    val peoplesToShowState : StateFlow<List<People>> = _peoplesToShowState.asStateFlow()

    private val _skeletonsShowState = MutableStateFlow<Boolean>(true)
    val skeletonsShowState : StateFlow<Boolean> = _skeletonsShowState.asStateFlow()

    private val _errorShowShar = MutableSharedFlow<Boolean>()
    val errorShowShar : SharedFlow<Boolean> = _errorShowShar.asSharedFlow()

    private val _sortByState = MutableStateFlow<SortBy>(SortBy.Alphabet)
    val sortByState : StateFlow<SortBy> = _sortByState.asStateFlow()

    private val screenActive = MutableStateFlow<Boolean>(false)

    init {
        observeActive()
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
        if (allPeoples.value.isNotEmpty()){
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
            _skeletonsShowState.value = false
            _peoplesToShowState.value = sorted
        }
    }

    //fake API call
    private fun loadData(){
        viewModelScope.launch {
            delay(500)
            _skeletonsShowState.value = true
            try {
                Log.d("MyLog", "Starting Api call")
                val people = loadPeoplesUseCase.invoke()
                Log.d("MyLog", "End result What we get $people")
                allPeoples.value = people
                setNewPeoples()
            } catch (e: Exception){
                Log.d("MyLog","Error catched -> $e")
                _errorShowShar.emit(true)
            }
        }
    }

    private fun observeActive(){
        viewModelScope.launch {
            screenActive.collect(){active->
                if (active && allPeoples.value.isEmpty()){
                    loadData()
                }
            }
        }
    }
}