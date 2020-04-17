package `in`.theekathir.newsreader.data.repository

import `in`.theekathir.newsreader.data.model.ArticlesResponse
import `in`.theekathir.newsreader.data.model.CustomConfig
import `in`.theekathir.newsreader.data.source.remote.ApiService
import `in`.theekathir.newsreader.domain.repository.ApiRepository

class NewsRepositoryImp(private val apiService: ApiService) : ApiRepository {
    override suspend fun getArticleByCategory(params: CustomConfig): ArticlesResponse {
        return apiService.getArticleByCategory(params)
    }
}