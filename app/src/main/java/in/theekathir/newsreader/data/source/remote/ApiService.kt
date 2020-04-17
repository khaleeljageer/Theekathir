package `in`.theekathir.newsreader.data.source.remote

import `in`.theekathir.newsreader.data.model.ArticlesResponse
import `in`.theekathir.newsreader.data.model.CustomConfig
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("/api/Article/GetArticleByCategory/")
    suspend fun getArticleByCategory(@Body params: CustomConfig): ArticlesResponse
}