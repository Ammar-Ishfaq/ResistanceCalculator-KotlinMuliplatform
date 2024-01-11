package com.ammar.resistorassistant.screens.list

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.ammar.resistorassistant.data.ResistorObject
import com.ammar.resistorassistant.data.AppRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class ListScreenModel(appRepository: AppRepository) : ScreenModel {
    val objects: StateFlow<List<ResistorObject>> =
        appRepository.getObjects()
            .stateIn(screenModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
}
