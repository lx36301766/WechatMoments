package pl.atlantischi.wechatmoments.utilities

import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.lzy.ninegrid.ImageInfo
import com.lzy.ninegrid.NineGridView
import com.lzy.ninegrid.preview.NineGridViewClickAdapter
import pl.atlantischi.wechatmoments.R
import pl.atlantischi.wechatmoments.data.model.Tweet

@BindingAdapter(value = ["imageUrl", "placeholder", "error"], requireAll = false)
fun ImageView.bindNetworkImage(url: String?, placeholderRes: Int, errorRes: Int) {
    if (url != null) {
        Glide.with(context).load(url)
            .placeholder(placeholderRes)
            .error(errorRes)
            .into(this)
    }
}

@BindingAdapter("imageUrls")
fun NineGridView.bindImages(images: Array<Tweet.Image?>?) {
    setAdapter(NineGridViewClickAdapter(context, images?.map { image ->
        ImageInfo().apply {
            thumbnailUrl = image?.url
            bigImageUrl = image?.url
        }
    }))
}

@BindingAdapter("comments")
fun LinearLayout.bindComments(comments: Array<Tweet.Comment?>?) {
    if (!comments.isNullOrEmpty()) {
        removeAllViews()
        comments.forEach { comment ->
            addView(TextView(context).apply {
                val commentText = "${comment?.sender?.nick} : ${comment?.content}"
                val builder = SpannableStringBuilder(commentText).apply {
                    setSpan(ForegroundColorSpan(resources.getColor(R.color.colorNickName)), 0,
                        comment?.sender?.nick?.length ?: 0, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                }
                text = builder
            })
        }
    }
}

//@BindingAdapter("bind:showForecast")
//fun LinearLayout.showForecast(weather: Weather?) = weather?.let {
//    removeAllViews()
//    for (forecast in it.forecastList) {
//        val view = LayoutInflater.from(context).inflate(R.layout.forecast_item, this, false)
//        DataBindingUtil.bind<ForecastItemBinding>(view)?.forecast = forecast
//        addView(view)
//    }
//}