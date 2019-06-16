package pl.atlantischi.wechatmoments

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.lzy.ninegrid.NineGridView

class MomentsApplication : Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = this

        NineGridView.setImageLoader(GlideImageLoader())
    }

    class GlideImageLoader : NineGridView.ImageLoader {

        override fun onDisplayImage(context: Context, imageView: ImageView, url: String) {
            Glide.with(context).load(url)
                .placeholder(android.R.drawable.btn_default)
                .error(android.R.drawable.btn_default)
                .into(imageView)
        }

        override fun getCacheImage(url: String): Bitmap? {
            return null
        }
    }

}