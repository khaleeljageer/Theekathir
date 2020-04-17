package `in`.theekathir.newsreader.utils

import android.annotation.SuppressLint
import okhttp3.OkHttpClient
import java.security.cert.X509Certificate
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.X509TrustManager

object UnsafeOkHttpClient {
    fun unsafeOkHttpClient(): OkHttpClient.Builder {
        val unsafeTrustManager = createUnsafeTrustManager()
        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(
            null,
            arrayOf(unsafeTrustManager), null
        )
        return OkHttpClient.Builder()
            .sslSocketFactory(
                sslContext.socketFactory, unsafeTrustManager
            ).hostnameVerifier(HostnameVerifier { _, _ ->
                true
            })
    }

    private fun createUnsafeTrustManager(): X509TrustManager {
        return object : X509TrustManager {
            @SuppressLint("TrustAllX509TrustManager")
            override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
            }

            @SuppressLint("TrustAllX509TrustManager")
            override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
            }

            override fun getAcceptedIssuers(): Array<out X509Certificate>? {
                return arrayOf()
            }
        }
    }
}
