package pl.atlantischi.wechatmoments.utilities

import android.app.Activity
import android.content.res.Resources
import android.os.Build
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.FloatRange

var DEFAULT_COLOR = 0
var DEFAULT_ALPHA = 0f
val MIN_API = 17

@JvmOverloads
fun Activity.immersive(color: Int = DEFAULT_COLOR, @FloatRange(from = 0.0, to = 1.0) alpha: Float = DEFAULT_ALPHA) {
    when {
        Build.VERSION.SDK_INT >= 21 -> {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = mixtureColor(color, alpha)
            var systemUiVisibility = window.decorView.systemUiVisibility
            systemUiVisibility = systemUiVisibility or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            systemUiVisibility = systemUiVisibility or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            window.decorView.systemUiVisibility = systemUiVisibility
        }
        Build.VERSION.SDK_INT >= 19 -> {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            setTranslucentView(window.decorView as ViewGroup, color, alpha)
        }
        Build.VERSION.SDK_INT >= MIN_API -> {
            var systemUiVisibility = window.decorView.systemUiVisibility
            systemUiVisibility = systemUiVisibility or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            systemUiVisibility = systemUiVisibility or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            window.decorView.systemUiVisibility = systemUiVisibility
        }
    }
}

fun Activity.setTranslucentView(container: ViewGroup, color: Int, @FloatRange(from = 0.0, to = 1.0) alpha: Float) {
    if (Build.VERSION.SDK_INT >= 19) {
        val mixtureColor = mixtureColor(color, alpha)
        var translucentView: View? = container.findViewById(android.R.id.custom)
        if (translucentView == null && mixtureColor != 0) {
            translucentView = View(this)
            translucentView.id = android.R.id.custom
            val lp = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight()
            )
            container.addView(translucentView, lp)
        }
        translucentView?.setBackgroundColor(mixtureColor)
    }
}

fun Activity.setPaddingSmart(view: View) {
    if (Build.VERSION.SDK_INT >= MIN_API) {
        val lp = view.layoutParams
        if (lp != null && lp.height > 0) {
            lp.height += getStatusBarHeight()//增高
        }
        view.setPadding(
            view.paddingLeft, view.paddingTop + getStatusBarHeight(),
            view.paddingRight, view.paddingBottom
        )
    }
}

fun Activity.setMargin(view: View) {
    if (Build.VERSION.SDK_INT >= MIN_API) {
        val lp = view.layoutParams
        if (lp is ViewGroup.MarginLayoutParams) {
            lp.topMargin += getStatusBarHeight()//增高
        }
        view.layoutParams = lp
    }
}


fun Activity.getStatusBarHeight(): Int {
    var result = 24
    val resId = resources.getIdentifier("status_bar_height", "dimen", "android")
    result = if (resId > 0) {
        resources.getDimensionPixelSize(resId)
    } else {
        TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            result.toFloat(),
            Resources.getSystem().displayMetrics
        ).toInt()
    }
    return result
}

fun mixtureColor(color: Int, @FloatRange(from = 0.0, to = 1.0) alpha: Float): Int {
    val a = if (color and -0x1000000 == 0) 0xff else color.ushr(24)
    return color and 0x00ffffff or ((a * alpha).toInt() shl 24)
}
