package `in`.theekathir.newsreader.ui.splash

import `in`.theekathir.newsreader.R
import `in`.theekathir.newsreader.ui.main.MainActivity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import androidx.appcompat.app.AppCompatActivity
import java.lang.ref.WeakReference

class SplashActivity : AppCompatActivity() {
    companion object {
        const val LAUNCH_NEXT_ACTIVITY = 101
    }

    private val splashHandler: SplashHandler by lazy {
        SplashHandler(this@SplashActivity)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        splashHandler.removeMessages(LAUNCH_NEXT_ACTIVITY)
        splashHandler.sendMessageDelayed(Message.obtain(splashHandler, LAUNCH_NEXT_ACTIVITY), 2000)
    }

    private class SplashHandler internal constructor(splashActivity: SplashActivity) : Handler() {
        val weekReference: WeakReference<SplashActivity> = WeakReference(splashActivity)
        override fun handleMessage(msg: Message) {
            val activity = weekReference.get()
            if (activity != null && !activity.isDestroyed) {
                when (msg.what) {
                    LAUNCH_NEXT_ACTIVITY -> {
                        activity.launchMainActivity()
                    }
                }
            }
        }
    }

    private fun launchMainActivity() {
        startActivity(Intent(baseContext, MainActivity::class.java))
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        splashHandler.removeMessages(LAUNCH_NEXT_ACTIVITY)
    }
}