package ro.dobrescuandrei.utils

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import java.io.Serializable

class Color
(
    val value : Int
) : Serializable
{
    constructor(code : String)
        : this(android.graphics.Color.parseColor(code))

    constructor(red : Int, green : Int, blue : Int, alpha : Int = 255)
        : this(android.graphics.Color.argb(alpha, red, green, blue))

    val red   : Int get() = android.graphics.Color.red(value)
    val green : Int get() = android.graphics.Color.green(value)
    val blue  : Int get() = android.graphics.Color.blue(value)
    val alpha : Int get() = android.graphics.Color.alpha(value)

    fun isDark() : Boolean
    {
        val darkness=1-(0.299*red+0.587*green+0.114*blue)/255
        return darkness>=0.5
    }

    fun isLight() : Boolean = !isDark()
}

object Colors
{
    val Black       : Color get() = Color(android.graphics.Color.BLACK)
    val Blue        : Color get() = Color(android.graphics.Color.BLUE)
    val Cyan        : Color get() = Color(android.graphics.Color.CYAN)
    val DarkGray    : Color get() = Color(android.graphics.Color.DKGRAY)
    val Gray        : Color get() = Color(android.graphics.Color.GRAY)
    val Green       : Color get() = Color(android.graphics.Color.GREEN)
    val LightGray   : Color get() = Color(android.graphics.Color.LTGRAY)
    val Magenta     : Color get() = Color(android.graphics.Color.MAGENTA)
    val Red         : Color get() = Color(android.graphics.Color.RED)
    val Transparent : Color get() = Color(android.graphics.Color.TRANSPARENT)
    val White       : Color get() = Color(android.graphics.Color.WHITE)
    val Yellow      : Color get() = Color(android.graphics.Color.YELLOW)
}

fun Context.getKolor(colorResourceId : Int) : Color =
    Color(ContextCompat.getColor(this, colorResourceId))

fun View.setBackgroundKolor(color : Color) =
    setBackgroundColor(color.value)

fun TextView.setTextKolor(color : Color) =
    setTextColor(color.value)
