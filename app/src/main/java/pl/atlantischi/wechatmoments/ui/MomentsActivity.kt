package pl.atlantischi.wechatmoments.ui

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.ButtonBarLayout
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.RefreshHeader
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener
import com.scwang.smartrefresh.layout.util.SmartUtil
import pl.atlantischi.wechatmoments.R
import pl.atlantischi.wechatmoments.databinding.ActivityMomentsBinding
import pl.atlantischi.wechatmoments.utilities.*
import kotlin.math.min

class MomentsActivity : AppCompatActivity() {

    private val viewModel by lazy { ViewModelProviders.of(this,
        InjectorUtil.provideMomentsViewModelFactory()).get(MomentsViewModel::class.java) }

    private val binding by lazy { DataBindingUtil.setContentView<ActivityMomentsBinding>(this, R.layout.activity_moments) }

    private var mOffset = 0
    private var mScrollY = 0

    private var mListPage = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewModel = viewModel

        immersive()
        initView()
        observerData()
        viewModel.getUserInfo()
    }

    private fun initView() {

        initTweetList()
        initRefreshLayout()
        initScrollView()
        setMargin(binding.classicsHeader)
        binding.classicsHeader.setDrawableMarginRight(-20.0f)

        binding.buttonBarLayout.alpha = 0f

        setPaddingSmart(binding.toolbar)
        binding.toolbar.setBackgroundColor(0)
        binding.toolbar.setNavigationOnClickListener { finish() }
    }

    private fun initScrollView() {
        var lastScrollY = 0
        val h = SmartUtil.dp2px(170f)
        val color = ContextCompat.getColor(applicationContext, R.color.colorPrimary) and 0x00ffffff
        binding.nestedScrollView.isNestedScrollingEnabled = true
        binding.nestedScrollView.setOnScrollChangeListener(
            NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
                var scrollY = scrollY
                if (lastScrollY < h) {
                    scrollY = Math.min(h, scrollY)
                    mScrollY = if (scrollY > h) h else scrollY
                    binding.buttonBarLayout.alpha = 1f * mScrollY / h
                    binding.toolbar.setBackgroundColor(255 * mScrollY / h shl 24 or color)
                    binding.profileImage.translationY = (mOffset - mScrollY).toFloat()
                }
                lastScrollY = scrollY
            })
    }

    private fun initRefreshLayout() {
        binding.refreshLayout.apply {
            setEnableLoadMore(true)
            setEnableAutoLoadMore(true)
            setEnableOverScrollDrag(true)
            setEnableOverScrollBounce(true)
            setOnMultiPurposeListener(object : SimpleMultiPurposeListener() {
                override fun onRefresh(refreshLayout: RefreshLayout) {
                    mListPage = 0
                    viewModel.getTweetList(mListPage)
                    (binding.tweetsList.adapter as TweetAdapter).setNewData(null)
                }

                override fun onLoadMore(refreshLayout: RefreshLayout) {
                    viewModel.getTweetList(++mListPage)
                }

                override fun onHeaderMoving(header: RefreshHeader?, isDragging: Boolean, percent: Float, offset: Int,
                                            headerHeight: Int, maxDragHeight: Int) {
                    mOffset = offset / 2
                    binding.profileImage.translationY = (mOffset - mScrollY).toFloat()
                    binding.toolbar.alpha = 1 - min(percent, 1f)
                }
            })
            autoRefresh()
        }
    }

    private fun initTweetList() {
        binding.tweetsList.apply {
            setHasFixedSize(false)
            isNestedScrollingEnabled = false
            addItemDecoration(DividerItemDecoration(this@MomentsActivity, DividerItemDecoration.VERTICAL))
            layoutManager = LinearLayoutManager(this@MomentsActivity)
            adapter = TweetAdapter()
        }
    }

    private fun observerData() {
//        viewModel.userInfo.observe(this, Observer { userInfo ->
//            binding.nickName.text = userInfo.nick
//            Glide.with(this).load(userInfo.avatar).into(binding.avatar)
//            Glide.with(this).load(userInfo.profileImage)
//                .placeholder(R.mipmap.profile_default)
//                .error(R.mipmap.profile_default)
//                .into(binding.profileImage)
//        })
        viewModel.tweetList.observe(this, Observer { tweetList ->
            binding.refreshLayout.finishRefresh()
            if (tweetList.size < 5) {
                binding.refreshLayout.finishLoadMoreWithNoMoreData()
            } else {
                binding.refreshLayout.finishLoadMore()
            }
            (binding.tweetsList.adapter as TweetAdapter).addData(tweetList)
        })

    }

}
