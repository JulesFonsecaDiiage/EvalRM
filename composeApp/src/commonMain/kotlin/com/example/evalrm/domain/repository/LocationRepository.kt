package com.example.evalrm.domain.repository

import com.example.evalrm.domain.model.Location

interface LocationRepository {
    suspend fun getLocations(forceRefresh: Boolean = false): List<Location>

    suspend fun getLocationDetail(id: Int, forceRefresh: Boolean = false): Location
}

