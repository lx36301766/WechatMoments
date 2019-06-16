package pl.atlantischi.wechatmoments

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.ButtonBarLayout
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.RefreshHeader
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener
import com.scwang.smartrefresh.layout.util.SmartUtil
import pl.atlantischi.wechatmoments.util.immersive
import pl.atlantischi.wechatmoments.util.setMargin
import pl.atlantischi.wechatmoments.util.setPaddingSmart
import pl.atlantischi.wechatmoments.data.model.Tweet
import pl.atlantischi.wechatmoments.util.bindView

class MomentsActivity : AppCompatActivity() {

    val toolbar: Toolbar by bindView(R.id.toolbar)
    val refreshLayout: SmartRefreshLayout by bindView(R.id.refreshLayout)
    val classicsHeader: ClassicsHeader by bindView(R.id.classics_header)
    val profileImage: ImageView by bindView(R.id.profile_image)
    val nickNameTv: TextView by bindView(R.id.nick_name)
    val avatarImage: ImageView by bindView(R.id.avatar)
    val tweetsList: RecyclerView by bindView(R.id.tweets_list)

    val scrollView: NestedScrollView by bindView(R.id.nested_scroll_view)
    val buttonBar: ButtonBarLayout by bindView(R.id.buttonBarLayout)

    val tweetList = mutableListOf<Tweet>(
        /*
        Tweet().apply {
            content = "沙发！"
            images = arrayOf("https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcRDy7HZaHxn15wWj6pXE4uMKAqHTC_uBgBlIzeeQSj2QaGgUzUmHg",
                "https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcTlJRALAf-76JPOLohBKzBg8Ab4Q5pWeQhF5igSfBflE_UYbqu7",
                "http://i.ytimg.com/vi/rGWI7mjmnNk/hqdefault.jpg")
            sender = Tweet.Sender().apply {
                username = "jport"
                nick = "Joe Portman"
                avatar = "http://info.thoughtworks.com/rs/thoughtworks2/images/glyph_badge.png"
            }
            comments = arrayOf(
                Tweet.Comment().apply {
                    content = "Good."
                    sender = Tweet.Sender().apply {
                        username = "outman"
                        nick = "Super hero"
                        avatar = "http://info.thoughtworks.com/rs/thoughtworks2/images/glyph_badge.png"
                    }
                },
                Tweet.Comment().apply {
                    content = "Like it too"
                    sender = Tweet.Sender().apply {
                        username = "inman"
                        nick = "Doggy Over"
                        avatar = "http://info.thoughtworks.com/rs/thoughtworks2/images/glyph_badge.png"
                    }
                }
            )
        },
        Tweet().apply {
            content = "沙发！"
            images = arrayOf("https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcRDy7HZaHxn15wWj6pXE4uMKAqHTC_uBgBlIzeeQSj2QaGgUzUmHg",
                "https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcTlJRALAf-76JPOLohBKzBg8Ab4Q5pWeQhF5igSfBflE_UYbqu7",
                "http://i.ytimg.com/vi/rGWI7mjmnNk/hqdefault.jpg")
            sender = Tweet.Sender().apply {
                username = "jport"
                nick = "Joe Portman"
                avatar = "http://info.thoughtworks.com/rs/thoughtworks2/images/glyph_badge.png"
            }
            comments = arrayOf(
                Tweet.Comment().apply {
                    content = "Good."
                    sender = Tweet.Sender().apply {
                        username = "outman"
                        nick = "Super hero"
                        avatar = "http://info.thoughtworks.com/rs/thoughtworks2/images/glyph_badge.png"
                    }
                },
                Tweet.Comment().apply {
                    content = "Like it too"
                    sender = Tweet.Sender().apply {
                        username = "inman"
                        nick = "Doggy Over"
                        avatar = "http://info.thoughtworks.com/rs/thoughtworks2/images/glyph_badge.png"
                    }
                }
            )
        },
        Tweet().apply {
            content = "沙发！"
            images = arrayOf("https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcRDy7HZaHxn15wWj6pXE4uMKAqHTC_uBgBlIzeeQSj2QaGgUzUmHg",
                "https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcTlJRALAf-76JPOLohBKzBg8Ab4Q5pWeQhF5igSfBflE_UYbqu7",
                "http://i.ytimg.com/vi/rGWI7mjmnNk/hqdefault.jpg")
            sender = Tweet.Sender().apply {
                username = "jport"
                nick = "Joe Portman"
                avatar = "http://info.thoughtworks.com/rs/thoughtworks2/images/glyph_badge.png"
            }
            comments = arrayOf(
                Tweet.Comment().apply {
                    content = "Good."
                    sender = Tweet.Sender().apply {
                        username = "outman"
                        nick = "Super hero"
                        avatar = "http://info.thoughtworks.com/rs/thoughtworks2/images/glyph_badge.png"
                    }
                },
                Tweet.Comment().apply {
                    content = "Like it too"
                    sender = Tweet.Sender().apply {
                        username = "inman"
                        nick = "Doggy Over"
                        avatar = "http://info.thoughtworks.com/rs/thoughtworks2/images/glyph_badge.png"
                    }
                }
            )
        },
        Tweet().apply {
            content = "沙发！"
            images = arrayOf("https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcRDy7HZaHxn15wWj6pXE4uMKAqHTC_uBgBlIzeeQSj2QaGgUzUmHg",
                "https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcTlJRALAf-76JPOLohBKzBg8Ab4Q5pWeQhF5igSfBflE_UYbqu7",
                "http://i.ytimg.com/vi/rGWI7mjmnNk/hqdefault.jpg")
            sender = Tweet.Sender().apply {
                username = "jport"
                nick = "Joe Portman"
                avatar = "http://info.thoughtworks.com/rs/thoughtworks2/images/glyph_badge.png"
            }
            comments = arrayOf(
                Tweet.Comment().apply {
                    content = "Good."
                    sender = Tweet.Sender().apply {
                        username = "outman"
                        nick = "Super hero"
                        avatar = "http://info.thoughtworks.com/rs/thoughtworks2/images/glyph_badge.png"
                    }
                },
                Tweet.Comment().apply {
                    content = "Like it too"
                    sender = Tweet.Sender().apply {
                        username = "inman"
                        nick = "Doggy Over"
                        avatar = "http://info.thoughtworks.com/rs/thoughtworks2/images/glyph_badge.png"
                    }
                }
            )
        },
        Tweet().apply {
            content = "沙发！"
            images = arrayOf("https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcRDy7HZaHxn15wWj6pXE4uMKAqHTC_uBgBlIzeeQSj2QaGgUzUmHg",
                "https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcTlJRALAf-76JPOLohBKzBg8Ab4Q5pWeQhF5igSfBflE_UYbqu7",
                "http://i.ytimg.com/vi/rGWI7mjmnNk/hqdefault.jpg")
            sender = Tweet.Sender().apply {
                username = "jport"
                nick = "Joe Portman"
                avatar = "http://info.thoughtworks.com/rs/thoughtworks2/images/glyph_badge.png"
            }
            comments = arrayOf(
                Tweet.Comment().apply {
                    content = "Good."
                    sender = Tweet.Sender().apply {
                        username = "outman"
                        nick = "Super hero"
                        avatar = "http://info.thoughtworks.com/rs/thoughtworks2/images/glyph_badge.png"
                    }
                },
                Tweet.Comment().apply {
                    content = "Like it too"
                    sender = Tweet.Sender().apply {
                        username = "inman"
                        nick = "Doggy Over"
                        avatar = "http://info.thoughtworks.com/rs/thoughtworks2/images/glyph_badge.png"
                    }
                }
            )
        },
        Tweet().apply {
            content = "沙发！"
            images = arrayOf("https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcRDy7HZaHxn15wWj6pXE4uMKAqHTC_uBgBlIzeeQSj2QaGgUzUmHg",
                "https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcTlJRALAf-76JPOLohBKzBg8Ab4Q5pWeQhF5igSfBflE_UYbqu7",
                "http://i.ytimg.com/vi/rGWI7mjmnNk/hqdefault.jpg")
            sender = Tweet.Sender().apply {
                username = "jport"
                nick = "Joe Portman"
                avatar = "http://info.thoughtworks.com/rs/thoughtworks2/images/glyph_badge.png"
            }
            comments = arrayOf(
                Tweet.Comment().apply {
                    content = "Good."
                    sender = Tweet.Sender().apply {
                        username = "outman"
                        nick = "Super hero"
                        avatar = "http://info.thoughtworks.com/rs/thoughtworks2/images/glyph_badge.png"
                    }
                },
                Tweet.Comment().apply {
                    content = "Like it too"
                    sender = Tweet.Sender().apply {
                        username = "inman"
                        nick = "Doggy Over"
                        avatar = "http://info.thoughtworks.com/rs/thoughtworks2/images/glyph_badge.png"
                    }
                }
            )
        },
        Tweet().apply {
            content = "沙发！"
            images = arrayOf("https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcRDy7HZaHxn15wWj6pXE4uMKAqHTC_uBgBlIzeeQSj2QaGgUzUmHg",
                "https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcTlJRALAf-76JPOLohBKzBg8Ab4Q5pWeQhF5igSfBflE_UYbqu7",
                "http://i.ytimg.com/vi/rGWI7mjmnNk/hqdefault.jpg")
            sender = Tweet.Sender().apply {
                username = "jport"
                nick = "Joe Portman"
                avatar = "http://info.thoughtworks.com/rs/thoughtworks2/images/glyph_badge.png"
            }
            comments = arrayOf(
                Tweet.Comment().apply {
                    content = "Good."
                    sender = Tweet.Sender().apply {
                        username = "outman"
                        nick = "Super hero"
                        avatar = "http://info.thoughtworks.com/rs/thoughtworks2/images/glyph_badge.png"
                    }
                },
                Tweet.Comment().apply {
                    content = "Like it too"
                    sender = Tweet.Sender().apply {
                        username = "inman"
                        nick = "Doggy Over"
                        avatar = "http://info.thoughtworks.com/rs/thoughtworks2/images/glyph_badge.png"
                    }
                }
            )
        },
        Tweet().apply {
            content = "沙发！"
            images = arrayOf("https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcRDy7HZaHxn15wWj6pXE4uMKAqHTC_uBgBlIzeeQSj2QaGgUzUmHg",
                "https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcTlJRALAf-76JPOLohBKzBg8Ab4Q5pWeQhF5igSfBflE_UYbqu7",
                "http://i.ytimg.com/vi/rGWI7mjmnNk/hqdefault.jpg")
            sender = Tweet.Sender().apply {
                username = "jport"
                nick = "Joe Portman"
                avatar = "http://info.thoughtworks.com/rs/thoughtworks2/images/glyph_badge.png"
            }
            comments = arrayOf(
                Tweet.Comment().apply {
                    content = "Good."
                    sender = Tweet.Sender().apply {
                        username = "outman"
                        nick = "Super hero"
                        avatar = "http://info.thoughtworks.com/rs/thoughtworks2/images/glyph_badge.png"
                    }
                },
                Tweet.Comment().apply {
                    content = "Like it too"
                    sender = Tweet.Sender().apply {
                        username = "inman"
                        nick = "Doggy Over"
                        avatar = "http://info.thoughtworks.com/rs/thoughtworks2/images/glyph_badge.png"
                    }
                }
            )
        },
        Tweet().apply {
            content = "沙发！"
            images = arrayOf("https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcRDy7HZaHxn15wWj6pXE4uMKAqHTC_uBgBlIzeeQSj2QaGgUzUmHg",
                "https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcTlJRALAf-76JPOLohBKzBg8Ab4Q5pWeQhF5igSfBflE_UYbqu7",
                "http://i.ytimg.com/vi/rGWI7mjmnNk/hqdefault.jpg")
            sender = Tweet.Sender().apply {
                username = "jport"
                nick = "Joe Portman"
                avatar = "http://info.thoughtworks.com/rs/thoughtworks2/images/glyph_badge.png"
            }
            comments = arrayOf(
                Tweet.Comment().apply {
                    content = "Good."
                    sender = Tweet.Sender().apply {
                        username = "outman"
                        nick = "Super hero"
                        avatar = "http://info.thoughtworks.com/rs/thoughtworks2/images/glyph_badge.png"
                    }
                },
                Tweet.Comment().apply {
                    content = "Like it too"
                    sender = Tweet.Sender().apply {
                        username = "inman"
                        nick = "Doggy Over"
                        avatar = "http://info.thoughtworks.com/rs/thoughtworks2/images/glyph_badge.png"
                    }
                }
            )
        },
        Tweet().apply {
            content = "沙发！"
            images = arrayOf("https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcRDy7HZaHxn15wWj6pXE4uMKAqHTC_uBgBlIzeeQSj2QaGgUzUmHg",
                "https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcTlJRALAf-76JPOLohBKzBg8Ab4Q5pWeQhF5igSfBflE_UYbqu7",
                "http://i.ytimg.com/vi/rGWI7mjmnNk/hqdefault.jpg")
            sender = Tweet.Sender().apply {
                username = "jport"
                nick = "Joe Portman"
                avatar = "http://info.thoughtworks.com/rs/thoughtworks2/images/glyph_badge.png"
            }
            comments = arrayOf(
                Tweet.Comment().apply {
                    content = "Good."
                    sender = Tweet.Sender().apply {
                        username = "outman"
                        nick = "Super hero"
                        avatar = "http://info.thoughtworks.com/rs/thoughtworks2/images/glyph_badge.png"
                    }
                },
                Tweet.Comment().apply {
                    content = "Like it too"
                    sender = Tweet.Sender().apply {
                        username = "inman"
                        nick = "Doggy Over"
                        avatar = "http://info.thoughtworks.com/rs/thoughtworks2/images/glyph_badge.png"
                    }
                }
            )
        }
    **/
    )

    inner class QuickAdapter : BaseQuickAdapter<Tweet, BaseViewHolder>(R.layout.item_tweet, tweetList) {

        override fun convert(viewHolder: BaseViewHolder, item: Tweet) {
            viewHolder.setText(R.id.nickname, item.sender?.nick)
                .setText(R.id.content, "${item.content} ${viewHolder.position}")
            Glide.with(mContext).load(item.sender?.avatar).into(viewHolder.getView<View>(R.id.avatar) as ImageView)

            Glide.with(mContext).load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1560606833412&di=7386459b32fb16bce7f03b4165cda7a5&imgtype=0&src=http%3A%2F%2Fpic2.52pk.com%2Ffiles%2Fallimg%2F090626%2F1553504U2-2.jpg").into(viewHolder.getView<View>(R.id.test_img) as ImageView)

            val commentWrapper = viewHolder.getView<LinearLayout>(R.id.comments_wrapper)

            item.comments?.let {
                for (comment in it) {
                    commentWrapper.addView(TextView(mContext).apply {
                        text = "${comment?.sender?.nick} : ${comment?.content}"
                    })
                }
            }
        }
    }

    private var mOffset = 0
    private var mScrollY = 0

    override fun onCreate(savedInstanceState: Bundle?) {

        ClassicsHeader.REFRESH_HEADER_PULLING = ""
        ClassicsHeader.REFRESH_HEADER_REFRESHING = ""
        ClassicsHeader.REFRESH_HEADER_LOADING = ""
        ClassicsHeader.REFRESH_HEADER_RELEASE = ""
        ClassicsHeader.REFRESH_HEADER_FINISH = ""
        ClassicsHeader.REFRESH_HEADER_FAILED = "刷新失败"
        ClassicsHeader.REFRESH_HEADER_UPDATE = ""

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_moments)

        toolbar.setNavigationOnClickListener { finish() }

        //状态栏透明和间距处理
        immersive()
        setPaddingSmart(toolbar)
        setMargin(classicsHeader)

        nickNameTv.text = "lxxxxxxxxxxxxx"
        Glide.with(this).load("http://info.thoughtworks.com/rs/thoughtworks2/images/glyph_badge.png").into(avatarImage)

        tweetsList.setHasFixedSize(false)
        tweetsList.isNestedScrollingEnabled = false
        tweetsList.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        tweetsList.layoutManager = LinearLayoutManager(this).apply {
            isAutoMeasureEnabled = true
        }
        tweetsList.adapter = QuickAdapter()

        classicsHeader.setDrawableMarginRight(-20.0f)

        refreshLayout.setEnableOverScrollDrag(true)
        refreshLayout.setEnableOverScrollBounce(true)
        refreshLayout.setOnMultiPurposeListener(object : SimpleMultiPurposeListener() {
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
        buttonBar.alpha = 0f
        toolbar.setBackgroundColor(0)

        Handler().postDelayed({
            tweetList.add(Tweet().apply {
                content = "沙发！"
                images = arrayOf("https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcRDy7HZaHxn15wWj6pXE4uMKAqHTC_uBgBlIzeeQSj2QaGgUzUmHg",
                    "https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcTlJRALAf-76JPOLohBKzBg8Ab4Q5pWeQhF5igSfBflE_UYbqu7",
                    "http://i.ytimg.com/vi/rGWI7mjmnNk/hqdefault.jpg")
                sender = Tweet.Sender().apply {
                    username = "jport"
                    nick = "Joe Portman"
                    avatar = "http://info.thoughtworks.com/rs/thoughtworks2/images/glyph_badge.png"
                }
                comments = arrayOf(
                    Tweet.Comment().apply {
                        content = "Good."
                        sender = Tweet.Sender().apply {
                            username = "outman"
                            nick = "Super hero"
                            avatar = "http://info.thoughtworks.com/rs/thoughtworks2/images/glyph_badge.png"
                        }
                    },
                    Tweet.Comment().apply {
                        content = "Like it too"
                        sender = Tweet.Sender().apply {
                            username = "inman"
                            nick = "Doggy Over"
                            avatar = "http://info.thoughtworks.com/rs/thoughtworks2/images/glyph_badge.png"
                        }
                    }
                )
            })

            tweetList.add(Tweet().apply {
                content = "沙发！"
                images = arrayOf("https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcRDy7HZaHxn15wWj6pXE4uMKAqHTC_uBgBlIzeeQSj2QaGgUzUmHg",
                    "https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcTlJRALAf-76JPOLohBKzBg8Ab4Q5pWeQhF5igSfBflE_UYbqu7",
                    "http://i.ytimg.com/vi/rGWI7mjmnNk/hqdefault.jpg")
                sender = Tweet.Sender().apply {
                    username = "jport"
                    nick = "Joe Portman"
                    avatar = "http://info.thoughtworks.com/rs/thoughtworks2/images/glyph_badge.png"
                }
                comments = arrayOf(
                    Tweet.Comment().apply {
                        content = "Good."
                        sender = Tweet.Sender().apply {
                            username = "outman"
                            nick = "Super hero"
                            avatar = "http://info.thoughtworks.com/rs/thoughtworks2/images/glyph_badge.png"
                        }
                    },
                    Tweet.Comment().apply {
                        content = "Like it too"
                        sender = Tweet.Sender().apply {
                            username = "inman"
                            nick = "Doggy Over"
                            avatar = "http://info.thoughtworks.com/rs/thoughtworks2/images/glyph_badge.png"
                        }
                    }
                )
            })
            tweetList.add(Tweet().apply {
                content = "沙发！"
                images = arrayOf("https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcRDy7HZaHxn15wWj6pXE4uMKAqHTC_uBgBlIzeeQSj2QaGgUzUmHg",
                    "https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcTlJRALAf-76JPOLohBKzBg8Ab4Q5pWeQhF5igSfBflE_UYbqu7",
                    "http://i.ytimg.com/vi/rGWI7mjmnNk/hqdefault.jpg")
                sender = Tweet.Sender().apply {
                    username = "jport"
                    nick = "Joe Portman"
                    avatar = "http://info.thoughtworks.com/rs/thoughtworks2/images/glyph_badge.png"
                }
                comments = arrayOf(
                    Tweet.Comment().apply {
                        content = "Good."
                        sender = Tweet.Sender().apply {
                            username = "outman"
                            nick = "Super hero"
                            avatar = "http://info.thoughtworks.com/rs/thoughtworks2/images/glyph_badge.png"
                        }
                    },
                    Tweet.Comment().apply {
                        content = "Like it too"
                        sender = Tweet.Sender().apply {
                            username = "inman"
                            nick = "Doggy Over"
                            avatar = "http://info.thoughtworks.com/rs/thoughtworks2/images/glyph_badge.png"
                        }
                    }
                )
            })
            tweetList.add(Tweet().apply {
                content = "沙发！"
                images = arrayOf("https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcRDy7HZaHxn15wWj6pXE4uMKAqHTC_uBgBlIzeeQSj2QaGgUzUmHg",
                    "https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcTlJRALAf-76JPOLohBKzBg8Ab4Q5pWeQhF5igSfBflE_UYbqu7",
                    "http://i.ytimg.com/vi/rGWI7mjmnNk/hqdefault.jpg")
                sender = Tweet.Sender().apply {
                    username = "jport"
                    nick = "Joe Portman"
                    avatar = "http://info.thoughtworks.com/rs/thoughtworks2/images/glyph_badge.png"
                }
                comments = arrayOf(
                    Tweet.Comment().apply {
                        content = "Good."
                        sender = Tweet.Sender().apply {
                            username = "outman"
                            nick = "Super hero"
                            avatar = "http://info.thoughtworks.com/rs/thoughtworks2/images/glyph_badge.png"
                        }
                    },
                    Tweet.Comment().apply {
                        content = "Like it too"
                        sender = Tweet.Sender().apply {
                            username = "inman"
                            nick = "Doggy Over"
                            avatar = "http://info.thoughtworks.com/rs/thoughtworks2/images/glyph_badge.png"
                        }
                    }
                )
            })

            tweetsList.adapter?.notifyDataSetChanged()

        } , 5000)
    }

}
