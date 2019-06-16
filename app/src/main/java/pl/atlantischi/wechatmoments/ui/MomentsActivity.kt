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

    val viewModel by lazy { ViewModelProviders.of(this,
        InjectorUtil.provideMomentsViewModelFactory(this)).get(MomentsViewModel::class.java) }

    val toolbar: Toolbar by bindView(R.id.toolbar)
    val refreshLayout: SmartRefreshLayout by bindView(R.id.refreshLayout)
    val classicsHeader: ClassicsHeader by bindView(R.id.classics_header)
    val profileImage: ImageView by bindView(R.id.profile_image)
    val nickNameTv: TextView by bindView(R.id.nick_name)
    val avatarImage: ImageView by bindView(R.id.avatar)
    val tweetsListView: RecyclerView by bindView(R.id.tweets_list)

    val scrollView: NestedScrollView by bindView(R.id.nested_scroll_view)
    val buttonBar: ButtonBarLayout by bindView(R.id.buttonBarLayout)

    private var mOffset = 0
    private var mScrollY = 0

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_moments)

        immersive()
        initView()
        observerData()
        viewModel.getUserInfo()
        viewModel.getTweetList()
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
            setEnableOverScrollDrag(true)
            setEnableOverScrollBounce(true)
            setOnMultiPurposeListener(object : SimpleMultiPurposeListener() {
                override fun onRefresh(refreshLayout: RefreshLayout) {
                    refreshLayout.finishRefresh(3000)
                }

                override fun onLoadMore(refreshLayout: RefreshLayout) {
                    refreshLayout.finishLoadMore(2000)
                }

                override fun onHeaderMoving(header: RefreshHeader?, isDragging: Boolean, percent: Float, offset: Int,
                                            headerHeight: Int, maxDragHeight: Int) {
                    mOffset = offset / 2
                    profileImage.translationY = (mOffset - mScrollY).toFloat()
                    toolbar.alpha = 1 - Math.min(percent, 1f)
                }
            })
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
            val result = tweetList.filter {
                if (it.content == null && (it.images == null || it.images?.size == 0)) {
                    return@filter false
                }
                return@filter true
            }
            (tweetsListView.adapter as TweetAdapter).addData(result)
        })

    }

}
