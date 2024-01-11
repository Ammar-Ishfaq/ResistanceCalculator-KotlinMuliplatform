package com.ammar.resistorassistant.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class AppRepository(
    private val appAPI: AppAPI,
    private val appStorage: AppStorage,
) {
    private val scope = CoroutineScope(SupervisorJob())

    fun initialize() {
        scope.launch {
            refresh()
        }
    }

    suspend fun refresh() {
        appStorage.saveObjects(appAPI.getResistorData())
    }

    fun getObjects(): Flow<List<ResistorObject>> = appStorage.getObjects()

    fun getObjectById(objectId: Int): Flow<ResistorObject?> = appStorage.getObjectById(objectId)
}
