package co.za.rain.myapplication.features.base.activity

import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import co.za.rain.myapplication.constants.ACTIVITY_TRANSITION
import co.za.rain.myapplication.constants.PAYLOAD_KEY
import co.za.rain.myapplication.features.base.fragments.BaseDialogFragment

abstract class BaseActivity : AppCompatActivity() {
    var activeDialogFragment: BaseDialogFragment? = null
    var isNewActivity: Boolean = false
    protected var hasImmersive: Boolean = false
    protected var cached: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initTransitions(this)
        isNewActivity = true
        supportActionBar?.elevation = 0f
    }

    open fun hasImmersive(ctx: Context): Boolean  {
        if (!cached) {
            if(Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                hasImmersive = false
                cached = true
                return false
            }
            var d =  (ctx.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay

            var realDisplayMetrics =  DisplayMetrics()
            d.getRealMetrics(realDisplayMetrics)

            var realHeight = realDisplayMetrics.heightPixels
            var realWidth = realDisplayMetrics.widthPixels

            var displayMetrics =  DisplayMetrics()
            d.getMetrics(displayMetrics)

            var displayHeight = displayMetrics.heightPixels
            var displayWidth = displayMetrics.widthPixels

            hasImmersive = (realWidth > displayWidth) || (realHeight > displayHeight)
            cached = true
        }

        return hasImmersive
    }

    private fun initTransitions(activity: Activity) {
        try {
            val activityTransition = activity.intent.getBundleExtra(PAYLOAD_KEY).getIntArray(ACTIVITY_TRANSITION)
            activity.overridePendingTransition(activityTransition!![0], activityTransition[1])
        }
        catch (e: Exception) {
            Log.e("AT", "$e")
        }
    }

}