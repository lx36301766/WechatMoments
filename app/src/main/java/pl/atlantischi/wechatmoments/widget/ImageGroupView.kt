package pl.atlantischi.wechatmoments.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.GridView
import android.widget.ImageView

/**
 * Custom image group view that like the wechat moments image group style
 */
class ImageGroupView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val singleImageView = ImageView(context).apply {
        visibility = GONE
    }

    private val mutliImageViewGrid = GridView(context).apply {
        numColumns = 3
        visibility = GONE
    }

    init {
        addView(singleImageView)
        addView(mutliImageViewGrid)
    }

    fun setImageData(imageList: List<String>?) {
        when (imageList?.size) {
            1 -> {
                singleImageView.visibility = VISIBLE
            }
            else -> {
                mutliImageViewGrid.visibility = VISIBLE
                if (imageList?.size == 4) {
                    mutliImageViewGrid.numColumns = 2
                }
                if (imageList?.size ?: 0 > 9) {

                }
            }
        }
    }

}