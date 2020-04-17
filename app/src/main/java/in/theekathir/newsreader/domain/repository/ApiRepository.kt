package `in`.theekathir.newsreader.domain.repository

import `in`.theekathir.newsreader.data.model.ArticlesResponse
import `in`.theekathir.newsreader.data.model.CustomConfig

interface ApiRepository {
    suspend fun getArticleByCategory(params: CustomConfig): ArticlesResponse
}