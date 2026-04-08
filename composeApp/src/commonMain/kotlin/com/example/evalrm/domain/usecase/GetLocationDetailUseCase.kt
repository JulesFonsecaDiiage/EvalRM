package com.example.evalrm.domain.usecase

import com.example.evalrm.domain.model.Location
import com.example.evalrm.domain.repository.LocationRepository

class GetLocationDetailUseCase(
    private val repository: LocationRepository,
) {
    suspend operator fun invoke(id: Int, forceRefresh: Boolean = false): Location {
        return repository.getLocationDetail(id, forceRefresh)
    }
}

