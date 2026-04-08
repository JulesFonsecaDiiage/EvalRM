package com.example.evalrm.data.repository

import com.example.evalrm.data.local.LocationLocalDataSource
import com.example.evalrm.data.mapper.toDomain
import com.example.evalrm.data.remote.RickAndMortyRemoteDataSource
import com.example.evalrm.domain.model.Location
import com.example.evalrm.domain.repository.LocationRepository

class LocationRepositoryImpl(
    private val remoteDataSource: RickAndMortyRemoteDataSource,
    private val localDataSource: LocationLocalDataSource,
) : LocationRepository {

    override suspend fun getLocations(forceRefresh: Boolean): List<Location> {
        val local = localDataSource.getCachedLocations()
        if (!forceRefresh && local.isNotEmpty()) return local

        // Fetch policy: remote is source of truth, local cache speeds up next reads.
        val remote = remoteDataSource.fetchLocations().map { it.toDomain() }
        localDataSource.cacheLocations(remote)
        return remote
    }

    override suspend fun getLocationDetail(id: Int, forceRefresh: Boolean): Location {
        val cached = localDataSource.getCachedLocationDetail(id)
        if (!forceRefresh && cached != null) return cached

        val remote = remoteDataSource.fetchLocationDetail(id).toDomain()
        localDataSource.cacheLocationDetail(remote)
        return remote
    }
}
