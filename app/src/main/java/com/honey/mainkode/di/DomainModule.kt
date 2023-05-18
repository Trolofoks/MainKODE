package com.honey.mainkode.di

import com.honey.repository.PeoplesRepository
import com.honey.usecase.FilterPeoplesUseCase
import com.honey.usecase.LoadPeoplesUseCase
import com.honey.usecase.SortPeoplesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @Provides
    fun provideSortPeoplesUseCase() : SortPeoplesUseCase{
        return SortPeoplesUseCase()
    }

    @Provides
    fun provideFilterPeoplesUseCase(): FilterPeoplesUseCase{
        return FilterPeoplesUseCase()
    }

    @Provides
    fun provideLoadPeoplesUseCase(repository: PeoplesRepository): LoadPeoplesUseCase{
        return LoadPeoplesUseCase(repository = repository)
    }
}