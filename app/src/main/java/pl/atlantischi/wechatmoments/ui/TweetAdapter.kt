package pl.atlantischi.wechatmoments.ui

import android.view.View
import android.view.View.INVISIBLE
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lzy.ninegrid.ImageInfo
import com.lzy.ninegrid.NineGridView
import com.lzy.ninegrid.preview.NineGridViewClickAdapter
import pl.atlantischi.wechatmoments.R
import pl.atlantischi.wechatmoments.data.model.Tweet

/**
 * Created on 2019-06-16.

 * @author lx
 */

class TweetAdapter : BaseQuickAdapter<Tweet, BaseViewHolder>(R.layout.item_tweet) {

    override fun convert(viewHolder: BaseViewHolder, item: Tweet) {
        viewHolder.setText(R.id.nickname, item.sender?.nick)
            .setText(R.id.content, item.content ?: "")
        Glide.with(mContext).load(item.sender?.avatar).into(viewHolder.getView<View>(R.id.avatar) as ImageView)

        val imageInfos = mutableListOf<ImageInfo>()
        item.images?.forEach { image ->
            imageInfos.add(ImageInfo().apply {
                thumbnailUrl = image?.url
                bigImageUrl = image?.url
            })
        }
        viewHolder.getView<NineGridView>(R.id.nine_grid).apply {
            setAdapter(NineGridViewClickAdapter(mContext, imageInfos))
        }

        val commentWrapper = viewHolder.getView<LinearLayout>(R.id.comments_wrapper)
        if (item.comments == null || item.comments?.size == 0) {
            commentWrapper.visibility = INVISIBLE
        } else {
            item.comments?.forEach { comment ->
                commentWrapper.addView(TextView(mContext).apply {
                    text = "${comment?.sender?.nick} : ${comment?.content}"
                })
            }
        }
    }
}
