package pl.atlantischi.wechatmoments.ui

import pl.atlantischi.wechatmoments.R
import pl.atlantischi.wechatmoments.data.model.Tweet
import pl.atlantischi.wechatmoments.databinding.ItemTweetBinding


/**
 * Created on 2019-06-16.

 * @author lx
 */

class TweetAdapter3 : BaseDataBindingAdapter<Tweet, ItemTweetBinding>(R.layout.item_tweet) {

    override fun convert(binding: ItemTweetBinding, item: Tweet) {
        binding.tweet = item
    }

}
