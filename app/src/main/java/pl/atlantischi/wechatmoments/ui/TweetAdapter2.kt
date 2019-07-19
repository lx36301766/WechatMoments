package pl.atlantischi.wechatmoments.ui

import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import pl.atlantischi.wechatmoments.R
import pl.atlantischi.wechatmoments.data.model.Tweet
import pl.atlantischi.wechatmoments.databinding.ItemTweetBinding


/**
 * Created on 2019-06-16.

 * @author lx
 */

class TweetAdapter2 : BaseQuickAdapter<Tweet, TweetAdapter2.TweetViewHolder>(R.layout.item_tweet) {

    override fun convert(viewHolder: TweetViewHolder, item: Tweet) {

        viewHolder.getBinding().run {
            tweet =  item
            executePendingBindings()
        }
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
