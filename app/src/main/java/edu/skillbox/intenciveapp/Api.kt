package edu.skillbox.intenciveapp

import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import kotlinx.serialization.json.Json

class Api {
    private val json = Json { ignoreUnknownKeys = true }
    private val apiClient = HttpClient {
        install(JsonFeature) {
            serializer = KotlinxSerializer(json)
        }
    }

    suspend fun loadPerson(id: Long): Person =
        apiClient.get("https://rickandmortyapi.com/api/character/$id")

    suspend fun loadPage(url: String): PersonPage = apiClient.get(url)
}