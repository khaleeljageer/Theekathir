package `in`.theekathir.newsreader

import `in`.theekathir.newsreader.di.networkModule
import `in`.theekathir.newsreader.di.newsModule
import `in`.theekathir.newsreader.di.persistenceModule
import `in`.theekathir.newsreader.utils.UnsafeOkHttpClient
import android.util.Log
import androidx.multidex.MultiDexApplication
import coil.Coil
import coil.ImageLoader
import coil.util.CoilUtils
import com.orhanobut.hawk.Hawk
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class TheekkathirApp : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        Coil.setDefaultImageLoader(::buildDefaultImageLoader)

        startKoin {
            androidLogger()
            androidContext(this@TheekkathirApp)
            modules(listOf(networkModule, newsModule, persistenceModule))
        }

        Hawk.init(applicationContext).setLogInterceptor { message ->
            if (BuildConfig.DEBUG) {
                Log.d("Khaleel", message)
            }
        }.build()
    }

    private fun buildDefaultImageLoader(): ImageLoader {
        return ImageLoader(this) {
            availableMemoryPercentage(0.10)
            bitmapPoolPercentage(0.5)
            crossfade(true)
            placeholder(R.drawable.img_placeholder)
            okHttpClient(
                UnsafeOkHttpClient.unsafeOkHttpClient()
                    .cache(CoilUtils.createDefaultCache(applicationContext)).build()
            )
        }
    }
}