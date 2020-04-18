package `in`.theekathir.newsreader.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ArticlesResponse(
    @Json(name = "CurrentPage")
    val currentPage: Int,
    @Json(name = "PageSize")
    val pageSize: Int,
    @Json(name = "TotalRecordCount")
    val totalCount: Int,
    @Json(name = "Articles")
    val articles: MutableList<Articles>,
    @Json(name = "RecentArticles")
    val recentArticles: MutableList<Articles>
)

@JsonClass(generateAdapter = true)
data class Articles(
    @Json(name = "ArticleId")
    val articleId: Long,
    @Json(name = "Title")
    var title: String,
    @Json(name = "ArticleLocation")
    val articleLocation: String,
    @Json(name = "ShortDescription")
    val shortDescription: String?,
    @Json(name = "CategoryName")
    val categoryName: String,
    @Json(name = "SubCategoryName")
    val subCategoryName: String,
    @Json(name = "ImageLocation")
    val imageLocation: String?,
    @Json(name = "ImageRegion")
    val imageRegion: String?,
    @Json(name = "ImageSize")
    val imageSize: String?,
    @Json(name = "PublishDate")
    var publishDate: String = "",
    var uiType: Int = 1
)