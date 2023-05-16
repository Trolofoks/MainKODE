package com.honey.mainkode.di

import com.honey.usecase.FilterPeoplesUseCase
import com.honey.usecase.SortPeoplesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

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
}