package `in`.theekathir.newsreader.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CategoriesResponse(
    @Json(name = "id")
    val id: Int,
    @Json(name = "text")
    val title: String
)