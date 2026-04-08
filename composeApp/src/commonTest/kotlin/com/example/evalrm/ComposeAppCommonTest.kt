package com.example.evalrm

import com.example.evalrm.domain.model.Location
import com.example.evalrm.domain.repository.LocationRepository
import com.example.evalrm.domain.usecase.GetLocationDetailUseCase
import com.example.evalrm.domain.usecase.GetLocationsUseCase
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals

class ComposeAppCommonTest {

    private val sampleLocation = Location(
        id = 1,
        name = "Earth",
        type = "Planet",
        dimension = "Dimension C-137",
        residents = listOf("r1", "r2"),
        url = "https://rickandmortyapi.com/api/location/1",
        created = "2017-11-10T12:42:04.162Z",
    )

    @Test
    fun residents_count_is_derived_from_residents_list() {
        assertEquals(2, sampleLocation.residentsCount)
    }

    @Test
    fun get_locations_use_case_delegates_to_repository() = runBlocking {
        val repository = FakeLocationRepository(listOf(sampleLocation))
        val useCase = GetLocationsUseCase(repository)

        val result = useCase()

        assertEquals(1, result.size)
        assertEquals("Earth", result.first().name)
    }

    @Test
    fun get_location_detail_use_case_returns_expected_item() = runBlocking {
        val repository = FakeLocationRepository(listOf(sampleLocation))
        val useCase = GetLocationDetailUseCase(repository)

        val result = useCase(1)

        assertEquals(1, result.id)
        assertEquals("Planet", result.type)
    }
}

private class FakeLocationRepository(
    private val data: List<Location>,
) : LocationRepository {
    override suspend fun getLocations(forceRefresh: Boolean): List<Location> = data

    override suspend fun getLocationDetail(id: Int, forceRefresh: Boolean): Location {
        return data.first { it.id == id }
    }
}