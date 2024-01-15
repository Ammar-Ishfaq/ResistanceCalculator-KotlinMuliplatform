package com.ammar.resistorassistant.di

import com.ammar.resistorassistant.data.InMemoryAppStorage
import com.ammar.resistorassistant.data.KtorAppAPI
import com.ammar.resistorassistant.data.AppAPI
import com.ammar.resistorassistant.data.AppRepository
import com.ammar.resistorassistant.data.AppStorage
import com.ammar.resistorassistant.screens.detail.DetailScreenModel
import com.ammar.resistorassistant.screens.list.ListScreenModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val dataModule = module {
    single {
        val json = Json { ignoreUnknownKeys = true }
        HttpClient {
            install(ContentNegotiation) {
                // TODO Fix API so it serves application/json
                json(json, contentType = ContentType.Any)
            }
        }
    }

    single<AppAPI> { KtorAppAPI(get()) }
    single<AppStorage> { InMemoryAppStorage() }
    single {
        AppRepository(get(), get()).apply {
            initialize()
        }
    }
}

val screenModelsModule = module {
    factoryOf(::ListScreenModel)
    factoryOf(::DetailScreenModel)
}

fun initKoin() {
    startKoin {
        modules(
            dataModule,
            screenModelsModule,
        )
    }
}
