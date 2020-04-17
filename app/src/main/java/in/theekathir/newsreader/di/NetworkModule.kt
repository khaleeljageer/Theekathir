package `in`.theekathir.newsreader.di

import `in`.theekathir.newsreader.BuildConfig
import `in`.theekathir.newsreader.data.repository.NewsRepositoryImp
import `in`.theekathir.newsreader.data.source.remote.ApiErrorHandle
import `in`.theekathir.newsreader.data.source.remote.ApiService
import `in`.theekathir.newsreader.domain.repository.ApiRepository
import `in`.theekathir.newsreader.domain.usecase.GetNewsUseCase
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit


val networkModule = module {
    single { createService(get()) }

    single { createOkHttpClient() }

    single { createRetrofit(get(), BuildConfig.NEWS_HOST_URL) }

    single { createMoshiConverterFactory() }

    single { createMoshi() }
}

fun createService(retrofit: Retrofit): ApiService {
    return retrofit.create(ApiService::class.java)
}

fun createMoshi(): Moshi {
    return Moshi.Builder().build()
}

fun createMoshiConverterFactory(): MoshiConverterFactory {
    return MoshiConverterFactory.create()
}

fun createOkHttpClient(): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    return OkHttpClient.Builder()
        .connectTimeout(60L, TimeUnit.SECONDS)
        .readTimeout(60L, TimeUnit.SECONDS)
        .writeTimeout(60L, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .addInterceptor(httpLoggingInterceptor)
        .build()
}

fun createRetrofit(okHttpClient: OkHttpClient, url: String): Retrofit {
    return Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
}

fun createApiErrorHandle(): ApiErrorHandle {
    return ApiErrorHandle()
}

fun createPostRepository(apiService: ApiService): ApiRepository {
    return NewsRepositoryImp(apiService)
}

fun createGetPostsUseCase(
    postsRepository: ApiRepository,
    apiErrorHandle: ApiErrorHandle
): GetNewsUseCase {
    return GetNewsUseCase(postsRepository, apiErrorHandle)
}

/*.addNetworkInterceptor(Interceptor { chain: Interceptor.Chain ->
    val response = chain.proceed(chain.request())
    val maxAge = 60

    response.newBuilder()
        .header("Cache-Control", "public, max-age=$maxAge")
        .removeHeader("Pragma")
        .build()
})
.addInterceptor(Interceptor { chain: Interceptor.Chain ->
    var request: Request = chain.request()
    if (!Utils.hasNetwork(context)) {
        val maxStale = 60 * 60 * 24 * 30
        request = request.newBuilder()
            .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
            .removeHeader("Pragma")
            .build()
    }
    chain.proceed(request)
})*/