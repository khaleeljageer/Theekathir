package `in`.theekathir.newsreader.presentation

import `in`.theekathir.newsreader.data.model.Articles

interface ListActionListener {
    fun onItemClick(articles: Articles)
    fun onShareClick(articles: Articles)
    fun onWhatsAppClick(articles: Articles)
}