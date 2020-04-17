package `in`.theekathir.newsreader.di

import `in`.theekathir.newsreader.ui.main.ArticleListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val newsModule = module {
    viewModel { ArticleListViewModel(get()) }

    single { createGetPostsUseCase(get(), createApiErrorHandle()) }

    single { createPostRepository(get()) }
}