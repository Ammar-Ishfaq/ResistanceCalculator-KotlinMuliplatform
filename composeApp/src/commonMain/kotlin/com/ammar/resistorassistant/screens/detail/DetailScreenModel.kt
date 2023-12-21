package com.ammar.resistorassistant.screens.detail

import cafe.adriel.voyager.core.model.ScreenModel
import com.ammar.resistorassistant.data.MuseumObject
import com.ammar.resistorassistant.data.MuseumRepository
import kotlinx.coroutines.flow.Flow

class DetailScreenModel(private val museumRepository: MuseumRepository) : ScreenModel {
    fun getObject(objectId: Int): Flow<MuseumObject?> =
        museumRepository.getObjectById(objectId)
}
