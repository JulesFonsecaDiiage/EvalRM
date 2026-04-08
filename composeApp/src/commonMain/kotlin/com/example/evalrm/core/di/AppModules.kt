package com.example.evalrm.core.di

import com.example.evalrm.data.local.LocationLocalDataSource
import com.example.evalrm.data.remote.RickAndMortyRemoteDataSource
import com.example.evalrm.data.repository.LocationRepositoryImpl
import com.example.evalrm.domain.repository.LocationRepository
import com.example.evalrm.domain.usecase.GetLocationDetailUseCase
import com.example.evalrm.domain.usecase.GetLocationsUseCase
import io.ktor.client.HttpClient
import org.koin.dsl.module

val appModule = module {
    single { HttpClient() }
    single { LocationLocalDataSource() }
    single { RickAndMortyRemoteDataSource(get()) }
    single<LocationRepository> { LocationRepositoryImpl(get(), get()) }

    factory { GetLocationsUseCase(get()) }
    factory { GetLocationDetailUseCase(get()) }
}

