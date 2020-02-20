package pl.atlantischi.wechatmoments

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.lzy.ninegrid.NineGridView
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import com.squareup.picasso.Picasso

class MomentsApplication : Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = this

        initializeThirdLibrary()
    }

    private fun initializeThirdLibrary() {

        ClassicsHeader.REFRESH_HEADER_PULLING = ""
        ClassicsHeader.REFRESH_HEADER_REFRESHING = ""
        ClassicsHeader.REFRESH_HEADER_LOADING = ""
        ClassicsHeader.REFRESH_HEADER_RELEASE = ""
        ClassicsHeader.REFRESH_HEADER_FINISH = ""
        ClassicsHeader.REFRESH_HEADER_FAILED = ""
        ClassicsHeader.REFRESH_HEADER_UPDATE = ""

        NineGridView.setImageLoader(object : NineGridView.ImageLoader {

            override fun getCacheImage(url: String?): Bitmap? {
                return null
            }

            override fun onDisplayImage(context: Context, imageView: ImageView, url: String?) {
                Picasso.with(context).load(url)
                    .placeholder(R.mipmap.image_default)
                    .error(R.mipmap.image_default)
                    .into(imageView)
            }
        })
    }

}
