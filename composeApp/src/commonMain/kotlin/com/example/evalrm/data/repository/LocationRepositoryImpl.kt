package com.example.evalrm.data.repository

import com.example.evalrm.data.local.LocationLocalDataSource
import com.example.evalrm.data.mapper.toDomain
import com.example.evalrm.data.remote.RickAndMortyRemoteDataSource
import com.example.evalrm.domain.model.Location
import com.example.evalrm.domain.repository.LocationRepository

/**
 * Implementation Data du contrat [LocationRepository].
 *
 * Politique de fetch (cas complexe cle de l'app):
 * - `forceRefresh == false`  -> lecture prioritaire du cache local si disponible
 * - `forceRefresh == true`   -> bypass cache et appel reseau immediat
 * - apres un appel distant   -> mapping DTO -> Domain puis ecriture cache
 *
 * Cette strategie garde le Domain independant des details techniques:
 * le contrat reste dans `domain/`, tandis que l'arbitrage local/remote est confine ici.
 * Elle garantit aussi une coherence simple entre listing et detail en partageant le meme cache local.
 */
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
