package com.example.evalrm.data.remote

import com.example.evalrm.data.remote.model.LocationRemote
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.intOrNull
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

class RickAndMortyRemoteDataSource(
    private val httpClient: HttpClient,
    private val json: Json = Json { ignoreUnknownKeys = true },
) {
    suspend fun fetchLocations(): List<LocationRemote> {
        val rawBody = httpClient.get("https://rickandmortyapi.com/api/location").bodyAsText()
        val root = json.parseToJsonElement(rawBody).jsonObject
        val results = root["results"]?.jsonArray ?: JsonArray(emptyList())
        return results.map { it.jsonObject.toLocationRemote() }
    }

    suspend fun fetchLocationDetail(id: Int): LocationRemote {
        val rawBody = httpClient.get("https://rickandmortyapi.com/api/location/$id").bodyAsText()
        return json.parseToJsonElement(rawBody).jsonObject.toLocationRemote()
    }

    private fun JsonObject.toLocationRemote(): LocationRemote = LocationRemote(
        id = this["id"]?.jsonPrimitive?.intOrNull ?: 0,
        name = this["name"]?.jsonPrimitive?.contentOrNull.orEmpty(),
        type = this["type"]?.jsonPrimitive?.contentOrNull.orEmpty(),
        dimension = this["dimension"]?.jsonPrimitive?.contentOrNull.orEmpty(),
        residents = this["residents"]?.jsonArray?.map { resident ->
            resident.jsonPrimitive.contentOrNull.orEmpty()
        }.orEmpty(),
        url = this["url"]?.jsonPrimitive?.contentOrNull.orEmpty(),
        created = this["created"]?.jsonPrimitive?.contentOrNull.orEmpty(),
    )
}

