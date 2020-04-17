package `in`.theekathir.newsreader.utils

object AppConstant {
    const val MEDIUM_IMAGE_BASE_URL = "http://www.theekkathir.in/Image/GetImage/MEDIUM/"
    const val LARGE_IMAGE_BASE_URL = "http://www.theekkathir.in/Image/GetImage/EXTRALARGE/"
    const val HEADER_CACHE_CONTROL = "Cache-Control"
    const val HEADER_PRAGMA = "Pragma"

    val CATEGORIES = arrayOf(
        "சமீபத்திய", "தேசம்", "உலகம்", "கட்டுரை",
        "தலையங்கம்", "அரசியல்", "பொருளாதாரம்", "விளையாட்டு", "பேஸ்புக்உலா"
    )
}

object PreferenceKey {
    const val CATEGORIES = "categories"
}

