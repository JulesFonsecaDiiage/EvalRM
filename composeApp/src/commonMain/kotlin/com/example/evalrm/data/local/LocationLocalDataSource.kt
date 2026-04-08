package com.example.evalrm.data.local

import com.example.evalrm.domain.model.Location

class LocationLocalDataSource {
    private var cachedLocations: List<Location> = emptyList()
    private val cachedDetails: MutableMap<Int, Location> = mutableMapOf()

    fun getCachedLocations(): List<Location> = cachedLocations

    fun cacheLocations(locations: List<Location>) {
        cachedLocations = locations
        locations.forEach { cachedDetails[it.id] = it }
    }

    fun getCachedLocationDetail(id: Int): Location? = cachedDetails[id]

    fun cacheLocationDetail(location: Location) {
        cachedDetails[location.id] = location
    }
}

