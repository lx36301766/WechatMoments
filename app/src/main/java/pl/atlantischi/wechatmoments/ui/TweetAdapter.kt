package pl.atlantischi.wechatmoments.ui

import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.widget.TextView
import pl.atlantischi.wechatmoments.R
import pl.atlantischi.wechatmoments.data.model.Tweet
import pl.atlantischi.wechatmoments.databinding.ItemTweetBinding
import pl.atlantischi.wechatmoments.utilities.BaseDataBindingAdapter

/**
 * Created on 2019-06-16.

 * @author lx
 */

class TweetAdapter : BaseDataBindingAdapter<Tweet, ItemTweetBinding>(R.layout.item_tweet) {

    override fun convert(binding: ItemTweetBinding, item: Tweet?) {
        binding.tweet = item
        if (!item?.comments.isNullOrEmpty()) {
            binding.commentsWrapper.run {
                removeAllViews()
                item?.comments?.forEach { comment ->
                    addView(TextView(context).apply {
                        val commentText = "${comment?.sender?.nick} : ${comment?.content}"
                        text = SpannableStringBuilder(commentText).apply {
                            setSpan(ForegroundColorSpan(resources.getColor(R.color.colorNickName)), 0,
                                comment?.sender?.nick?.length ?: 0, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                        }
                    })
                }
            }
        }
    }

}
