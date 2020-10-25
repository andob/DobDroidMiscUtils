package ro.dobrescuandrei.utils

import android.app.Activity
import android.content.Context
import android.graphics.Point
import android.os.Build
import android.view.WindowInsets
import android.view.WindowManager

object ScreenSize
{
    var width : Int = 0
    var height : Int = 0

    fun init(withContext : Context)
    {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.R)
        {
            val windowMetrics=(withContext as Activity).windowManager.currentWindowMetrics
            val insets=windowMetrics.windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
            width=windowMetrics.bounds.width()-insets.left-insets.right
            height=windowMetrics.bounds.height()-insets.top-insets.bottom
        }

        val windowManager=withContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display=windowManager.defaultDisplay!!
        val size=Point()
        display.getSize(size)
        width=size.x
        height=size.y
    }
}