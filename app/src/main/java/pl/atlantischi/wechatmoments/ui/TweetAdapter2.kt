package pl.atlantischi.wechatmoments.ui

import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import pl.atlantischi.wechatmoments.BR
import pl.atlantischi.wechatmoments.R
import pl.atlantischi.wechatmoments.data.model.Tweet
import pl.atlantischi.wechatmoments.databinding.ItemTweetBinding


/**
 * Created on 2019-06-16.

 * @author lx
 */

class TweetAdapter2 : BaseQuickAdapter<Tweet, TweetAdapter2.TweetViewHolder>(R.layout.item_tweet) {

    override fun convert(viewHolder: TweetViewHolder, item: Tweet) {

//        viewHolder.setText(R.id.nickname, item.sender?.nick)
//            .setText(R.id.content, item.content ?: "")
//        Glide.with(mContext).load(item.sender?.avatar).into(viewHolder.getView<View>(R.id.avatar) as ImageView)

//        val imageInfos = mutableListOf<ImageInfo>()
//        item.images?.forEach { image ->
//            imageInfos.add(ImageInfo().apply {
//                thumbnailUrl = image?.url
//                bigImageUrl = image?.url
//            })
//        }
//        viewHolder.getView<NineGridView>(R.id.nine_grid).apply {
//            setAdapter(NineGridViewClickAdapter(mContext, imageInfos))
//        }

        viewHolder.getBinding().run {
            tweet =  item
            executePendingBindings()
        }

//        val binding = viewHolder.getBinding()
//        binding.setVariable(BR.tweet, item)
//        binding.executePendingBindings()

//        val commentWrapper = viewHolder.getView<LinearLayout>(R.id.comments_wrapper)
//        if (item.comments.isNullOrEmpty()) {
//            commentWrapper.visibility = INVISIBLE
//        } else {
//            commentWrapper.removeAllViews()
//            commentWrapper.visibility = VISIBLE
//            item.comments?.forEach { comment ->
//                commentWrapper.addView(TextView(mContext).apply {
//                    val commentText = "${comment?.sender?.nick} : ${comment?.content}"
//                    val builder = SpannableStringBuilder(commentText).apply {
//                        setSpan(ForegroundColorSpan(resources.getColor(R.color.colorNickName)), 0, comment?.sender?.nick?.length ?: 0, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//                    }
//                    text = builder
//                })
//            }
//        }
    }

    override fun getItemView(layoutResId: Int, parent: ViewGroup): View {
        val binding = DataBindingUtil.inflate<ItemTweetBinding>(mLayoutInflater, layoutResId, parent, false)
            ?: return super.getItemView(layoutResId, parent)
        return binding.root.apply { setTag(R.id.BaseQuickAdapter_databinding_support, binding) }
    }

    class TweetViewHolder(view: View?) : BaseViewHolder(view) {

        fun getBinding() = itemView.getTag(R.id.BaseQuickAdapter_databinding_support) as ItemTweetBinding

    }

}
