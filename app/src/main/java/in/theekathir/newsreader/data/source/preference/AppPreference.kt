package `in`.theekathir.newsreader.data.source.preference

import `in`.theekathir.newsreader.utils.PreferenceKey
import com.orhanobut.hawk.Hawk

class AppPreference {
    fun getCategories(): List<String> = Hawk.get(PreferenceKey.CATEGORIES, emptyList())
    fun setCategories(categories: List<String>) = Hawk.put(PreferenceKey.CATEGORIES, categories)
}