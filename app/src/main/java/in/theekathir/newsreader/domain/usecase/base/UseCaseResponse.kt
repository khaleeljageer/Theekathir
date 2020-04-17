package `in`.theekathir.newsreader.domain.usecase.base

import `in`.theekathir.newsreader.data.model.ErrorModel

interface UseCaseResponse<Type> {

    fun onSuccess(result: Type)

    fun onError(errorModel: ErrorModel?)
}

