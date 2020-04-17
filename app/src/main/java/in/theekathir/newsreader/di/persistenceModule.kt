package `in`.theekathir.newsreader.di

import `in`.theekathir.newsreader.data.source.preference.AppPreference
import org.koin.dsl.module

val persistenceModule = module {
    single {
        AppPreference()
    }
}