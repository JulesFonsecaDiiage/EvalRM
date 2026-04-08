package com.example.evalrm.domain.usecase

import com.example.evalrm.domain.model.Location
import com.example.evalrm.domain.repository.LocationRepository

class GetLocationsUseCase(
    private val repository: LocationRepository,
) {
    suspend operator fun invoke(forceRefresh: Boolean = false): List<Location> {
        return repository.getLocations(forceRefresh)
    }
}

