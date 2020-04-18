package `in`.theekathir.newsreader.ui.main

import `in`.theekathir.newsreader.data.model.*
import `in`.theekathir.newsreader.domain.usecase.GetNewsUseCase
import `in`.theekathir.newsreader.domain.usecase.base.UseCaseResponse
import `in`.theekathir.newsreader.presentation.base.BaseViewModel
import `in`.theekathir.newsreader.utils.publishedTime
import `in`.theekathir.newsreader.utils.replaceSpace
import android.util.Log
import androidx.lifecycle.MutableLiveData

class ArticleListViewModel constructor(private val getNewsUseCase: GetNewsUseCase) :
    BaseViewModel() {
    val articlesResponse = MutableLiveData<ArticlesResponse>()

    private var pageNumber = 0
    var totalCount = 0

    fun loadPosts(category: String) {
        val params = CustomConfig(category, "MEDIUM", pageNumber + 1, 10)
        showProgressbar.value = true
        getNewsUseCase.invoke(scope, params, object : UseCaseResponse<ArticlesResponse> {
            override fun onSuccess(result: ArticlesResponse) {
                pageNumber = result.currentPage
                totalCount = result.totalCount
                if (result.articles.size > 0) {
                    result.apply {
                        for (article in this.articles) {
                            article.publishDate = article.publishDate.publishedTime()
                            article.title = article.title.replaceSpace()
                            article.uiType = if (article.title.length > 65) {
                                2
                            } else {
                                1
                            }
                        }
                    }
                    articlesResponse.value = result
                }

                showProgressbar.value = false
            }

            override fun onError(errorModel: ErrorModel?) {
                Log.d("Khaleel", "onError : ${errorModel?.message}")
                messageData.value = errorModel?.message
                showProgressbar.value = false
            }
        })
    }
}