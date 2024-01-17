package com.ammar.resistorassistant.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.utils.io.CancellationException

interface AppAPI {
    suspend fun getResistorData(): List<ResistorObject>
}

class KtorAppAPI(private val client: HttpClient) : AppAPI {
    companion object {
        private const val API_URL =
            "https://raw.githubusercontent.com/Ammar-Ishfaq/ResistanceCalculator/main/ResistanceGuide.json?token=GHSAT0AAAAAACIUMB32YHSYAGLGZ2PF7ESWZNHS6EQ"
    }

    override suspend fun getResistorData(): List<ResistorObject> {
        return try {
            client.get(API_URL).body()
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            e.printStackTrace()

            emptyList()
        }
    }
}
