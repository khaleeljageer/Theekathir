package `in`.theekathir.newsreader.utils

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.text.format.DateUtils
import android.widget.Toast
import java.net.URLEncoder
import java.text.SimpleDateFormat
import java.util.*

fun String.replaceSpace(): String = this.trim().replace(" +", " ")

fun Int.toPixel(context: Context): Float = (this * context.resources.displayMetrics.density)

fun String.publishedTime(): String {
    return try {
        val simpleFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val date = simpleFormat.parse(this) as Date
        val now = System.currentTimeMillis()

        val ago = DateUtils.getRelativeTimeSpanString(date.time, now, DateUtils.MINUTE_IN_MILLIS)
        return ago.toString()
    } catch (e: Exception) {
        ""
    }
}

fun Context.hasNetwork(): Boolean {
    var result = false
    val connectivityManager =
        this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val actNw =
            connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
        result = when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    } else {
        connectivityManager.run {
            connectivityManager.activeNetworkInfo?.run {
                result = when (type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    else -> false
                }

            }
        }
    }
    return result
}

fun Context.shareInOtherApps(shareMessage: String) {
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, shareMessage)
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, "Share with...")
    startActivity(shareIntent)
}

fun Context.shareInWhatsApp(shareMessage: String) {
    val packageManager = this.packageManager
    val i = Intent(Intent.ACTION_VIEW)
    val url =
        "https://api.whatsapp.com/send?text=" + URLEncoder.encode(shareMessage, "UTF-8")
    i.data = Uri.parse(url)
    if (i.resolveActivity(packageManager) != null) {
        this.startActivity(i)
    } else {
        Toast.makeText(this, "WhatsApp not installed", Toast.LENGTH_SHORT).show()
    }
}

fun String.appendAppUrl(): String {
    return this.plus("\n\nShared via:\nTheekathir App\nhttps://bit.ly/3bjrLwR")
}

fun String.buildShareUrl(
    categoryName: String,
    subCategoryName: String,
    articleLocation: String
): String {
    return this.plus(URLEncoder.encode("$categoryName/$subCategoryName/$articleLocation", "UTF-8"))
}