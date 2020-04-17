package `in`.theekathir.newsreader.domain.usecase

import `in`.theekathir.newsreader.data.model.ArticlesResponse
import `in`.theekathir.newsreader.data.model.CustomConfig
import `in`.theekathir.newsreader.data.source.remote.ApiErrorHandle
import `in`.theekathir.newsreader.domain.repository.ApiRepository
import `in`.theekathir.newsreader.domain.usecase.base.UseCase

class GetNewsUseCase constructor(
    private val postsRepository: ApiRepository,
    apiErrorHandle: ApiErrorHandle?
) : UseCase<ArticlesResponse, Any?>(apiErrorHandle) {

    override suspend fun run(params: Any?): ArticlesResponse {
        return postsRepository.getArticleByCategory(params as CustomConfig)
    }
}