package com.ammar.resistorassistant.screens.detail

import cafe.adriel.voyager.core.model.ScreenModel
import com.ammar.resistorassistant.data.ResistorObject
import com.ammar.resistorassistant.data.AppRepository
import kotlinx.coroutines.flow.Flow

class DetailScreenModel(private val appRepository: AppRepository) : ScreenModel {
    fun getObject(objectId: Int): Flow<ResistorObject?> =
        appRepository.getObjectById(objectId)
}
