package com.ammar.resistorassistant.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

interface AppStorage {
    suspend fun saveObjects(newObjects: List<ResistorObject>)

    fun getObjectById(objectId: Int): Flow<ResistorObject?>

    fun getObjects(): Flow<List<ResistorObject>>
}

class InMemoryAppStorage : AppStorage {
    private val storedObjects = MutableStateFlow(emptyList<ResistorObject>())

    override suspend fun saveObjects(newObjects: List<ResistorObject>) {
        storedObjects.value = newObjects
    }

    override fun getObjectById(id: Int): Flow<ResistorObject?> {
        return storedObjects.map { objects ->
            objects.find { it.id == id }
        }
    }

    override fun getObjects(): Flow<List<ResistorObject>> = storedObjects
}
