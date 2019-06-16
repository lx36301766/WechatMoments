package pl.atlantischi.wechatmoments.ui

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.ButtonBarLayout
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
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
import pl.atlantischi.wechatmoments.utilities.*

class MomentsActivity : AppCompatActivity() {

    private val viewModel by lazy { ViewModelProviders.of(this,
        InjectorUtil.provideMomentsViewModelFactory(this)).get(MomentsViewModel::class.java) }

    private val toolbar: Toolbar by bindView(R.id.toolbar)
    private val refreshLayout: SmartRefreshLayout by bindView(R.id.refreshLayout)
    private val classicsHeader: ClassicsHeader by bindView(R.id.classics_header)
    private val profileImage: ImageView by bindView(R.id.profile_image)
    private val nickNameTv: TextView by bindView(R.id.nick_name)
    private val avatarImage: ImageView by bindView(R.id.avatar)
    private val tweetsListView: RecyclerView by bindView(R.id.tweets_list)

    private val scrollView: NestedScrollView by bindView(R.id.nested_scroll_view)
    private val buttonBar: ButtonBarLayout by bindView(R.id.buttonBarLayout)

    private var mOffset = 0
    private var mScrollY = 0

    private var mListPage = 0

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_moments)

        immersive()
        initView()
        observerData()
        viewModel.getUserInfo()
    }

    private fun initView() {
        initTweetList()
        initRefreshLayout()
        initScrollView()
        setMargin(classicsHeader)
        classicsHeader.setDrawableMarginRight(-20.0f)
        buttonBar.alpha = 0f
        setPaddingSmart(toolbar)
        toolbar.setBackgroundColor(0)
        toolbar.setNavigationOnClickListener { finish() }
    }

    private fun initScrollView() {
        var lastScrollY = 0
        val h = SmartUtil.dp2px(170f)
        val color = ContextCompat.getColor(applicationContext, R.color.colorPrimary) and 0x00ffffff
        scrollView.isNestedScrollingEnabled = true
        scrollView.setOnScrollChangeListener(
            NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
                var scrollY = scrollY
                if (lastScrollY < h) {
                    scrollY = Math.min(h, scrollY)
                    mScrollY = if (scrollY > h) h else scrollY
                    buttonBar.alpha = 1f * mScrollY / h
                    toolbar.setBackgroundColor(255 * mScrollY / h shl 24 or color)
                    profileImage.translationY = (mOffset - mScrollY).toFloat()
                }
                lastScrollY = scrollY
            })
    }

    private fun initRefreshLayout() {
        refreshLayout.apply {
            setEnableLoadMore(true)
            setEnableAutoLoadMore(true)
            setEnableOverScrollDrag(true)
            setEnableOverScrollBounce(true)
            setOnMultiPurposeListener(object : SimpleMultiPurposeListener() {
                override fun onRefresh(refreshLayout: RefreshLayout) {
                    mListPage = 0
                    viewModel.getTweetList(mListPage)
                    (tweetsListView.adapter as TweetAdapter).setNewData(null)
                }

                override fun onLoadMore(refreshLayout: RefreshLayout) {
                    viewModel.getTweetList(++mListPage)
                }

                override fun onHeaderMoving(header: RefreshHeader?, isDragging: Boolean, percent: Float, offset: Int,
                                            headerHeight: Int, maxDragHeight: Int) {
                    mOffset = offset / 2
                    profileImage.translationY = (mOffset - mScrollY).toFloat()
                    toolbar.alpha = 1 - Math.min(percent, 1f)
                }
            })
            autoRefresh()
        }
    }

    private fun initTweetList() {
        tweetsListView.apply {
            setHasFixedSize(false)
            isNestedScrollingEnabled = false
            addItemDecoration(DividerItemDecoration(this@MomentsActivity, DividerItemDecoration.VERTICAL))
            layoutManager = LinearLayoutManager(this@MomentsActivity)
            adapter = TweetAdapter()
        }
    }

    private fun observerData() {
        viewModel.userInfo.observe(this, Observer { userInfo ->
            nickNameTv.text = userInfo.nick
            Glide.with(this).load(userInfo.avatar).into(avatarImage)
            Glide.with(this).load(userInfo.profileImage)
                .placeholder(R.mipmap.profile_default)
                .error(R.mipmap.profile_default)
                .into(profileImage)
        })
        viewModel.tweetList.observe(this, Observer { tweetList ->
            refreshLayout.finishRefresh()
            if (tweetList.size < 5) {
                refreshLayout.finishLoadMoreWithNoMoreData()
            } else {
                refreshLayout.finishLoadMore()
            }
            (tweetsListView.adapter as TweetAdapter).addData(tweetList)
        })

    }

}
