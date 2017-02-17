package com.dw.applebuy.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dw.applebuy.R;
import com.dw.applebuy.been.Info;
import com.dw.applebuy.ui.MainActivity;
import com.dw.applebuy.ui.home.commentmanage.CommentActivity;
import com.dw.applebuy.ui.home.hexiao.CodeScanActivity;
import com.dw.applebuy.ui.home.ordermanage.OrderListActivity;
import com.dw.applebuy.ui.home.renzheng.p.InfoUtil;
import com.dw.applebuy.ui.home.scoremanage.ScoreActivity;
import com.dw.applebuy.ui.home.shoppingmanage.ShoppingManagerActivity;
import com.dw.applebuy.ui.home.usermanage.UserActivity;
import com.dw.applebuy.util.RenZhengHelp;
import com.example.qr_codescan.MipcaActivityCapture;
import com.wlj.base.adapter.CommonAdapter;
import com.wlj.base.adapter.ImagePagerAdapter;
import com.wlj.base.adapter.ViewHolder;
import com.wlj.base.util.GoToHelp;
import com.wlj.base.widget.AutoScrollViewPager;
import com.wlj.base.widget.SwitchViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeFragment extends Fragment implements ViewPager.OnPageChangeListener {

    private static final int CODESCANRQ = 4321;
    @BindView(R.id.title_left)
    TextView titleLeft;
    @BindView(R.id.title_title)
    TextView titleTitle;
    @BindView(R.id.title_right)
    TextView titleRight;
    @BindView(R.id.home_yirenzheng_viewpager)
    AutoScrollViewPager viewPager;
    @BindView(R.id.home_yirenzheng_number)
    TextView imageNumber;
    //    @BindView(R.id.home_yirenzheng_layout)
//    FrameLayout homeYirenzhengLayout;
    @BindView(R.id.home_lijirenzheng_layout)
    RelativeLayout homeLijirenzhengLayout;
    @BindView(R.id.home_gridview)
    GridView homeGridview;
    @BindView(R.id.home_renzhengimage)
    ImageView renzhengimage;
    @BindView(R.id.home_renzhengtext)
    TextView homeRenzhengtext;
//    @BindView(R.id.home_renzheng_defimage)
//    ImageView defimage;

    private List<GridItem> mDatas;
    private CommonAdapter<GridItem> gridviewAdapter;
    private String authenticate_status;
    private RenZhengHelp renZhengHelp;
    private ImagePagerAdapter<Info.ImgsBean> adapter;
    private List<Info.ImgsBean> list;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        InfoUtil.getInstall().getInfoFromWeb(getActivity(), new InfoUtil.InfoBack() {
            @Override
            public void back(Info info) {
                //认证状态(1:未认证  2:认证中  3:已认证  4:认证失败)
                authenticate_status = info.getAuthenticate_status();
                MainActivity activity = (MainActivity) getActivity();
                activity.authenticate_status = authenticate_status;


                switch (authenticate_status) {
                    case RenZhengHelp.renzheng_no:
                        homeLijirenzhengLayout.setVisibility(View.VISIBLE);
                        renzhengimage.setImageResource(R.drawable.icon_renzheng);
                        homeRenzhengtext.setText("请完成实名认证，保护商家权益");

                        imageNumber.setVisibility(View.GONE);
                        titleTitle.setText("尊敬的商户，你好！");
                        break;
                    case RenZhengHelp.renzheng_ing:
                        homeLijirenzhengLayout.setVisibility(View.VISIBLE);
                        renzhengimage.setImageResource(R.drawable.icon_renzhenging);
                        homeRenzhengtext.setText("你的认证已提交，请等待审核");

                        imageNumber.setVisibility(View.GONE);
                        titleTitle.setText("尊敬的商户，你好！");
                        break;
                    case RenZhengHelp.renzheng_ed:
                        homeLijirenzhengLayout.setVisibility(View.GONE);

//                        imageNumber.setVisibility(View.GONE);
                        titleTitle.setText(info.getName());

                        adapter.setData(info.getImgs());
                        viewPager.startAutoScroll();
                        break;
                    case RenZhengHelp.renzheng_fail:
                        homeLijirenzhengLayout.setVisibility(View.VISIBLE);
                        renzhengimage.setImageResource(R.drawable.icon_renzheng_fail);
                        homeRenzhengtext.setText("认证失败，请重新提交认证");

                        imageNumber.setVisibility(View.GONE);
                        titleTitle.setText("尊敬的商户，你好！");
                        break;
                }
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        viewPager.stopAutoScroll();
    }

    private void initView() {
        renZhengHelp = RenZhengHelp.getInstall(getActivity());
        initGridview();

        initAutoScrollViewPager();
    }

    private void initAutoScrollViewPager() {

        viewPager.setInterval(3000);// 设置自动滚动的间隔时间，单位为毫秒
        viewPager.setSlideBorderMode(AutoScrollViewPager.SLIDE_BORDER_MODE_CYCLE);// 滑动到第一个或最后一个Item的处理方式，支持没有任何操作、轮播以及传递到父View三种模式
        viewPager.setAutoScrollDurationFactor(4);//设置ViewPager滑动动画间隔时间的倍率，达到减慢动画或改变动画速度的效果
//		viewPager.setDirection(AutoScrollViewPager.RIGHT); // 设置自动滚动的方向，默认向右
//		viewPager.setCycle(true);// 是否自动循环轮播，默认为true
//		viewPager.setStopScrollWhenTouch(true);// 当手指碰到ViewPager时是否停止自动滚动，默认为true
//		viewPager.setBorderAnimation(true);// 设置循环滚动时滑动到从边缘滚动到下一个是否需要动画，默认为true

        list = new ArrayList<>();
        adapter = getAdapter(list).setInfiniteLoop(true);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(this);
    }

    @NonNull
    private ImagePagerAdapter<Info.ImgsBean> getAdapter(List<Info.ImgsBean> list) {
        return new ImagePagerAdapter<Info.ImgsBean>(list) {
            @Override
            public View getPageItemview(Info.ImgsBean item, View view, ViewGroup container) {
                if (view == null) {
                    view = new ImageView(getActivity());
                }
                ImageView imageView1 = (ImageView) view;

                imageView1.setAdjustViewBounds(true);
                imageView1.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView1.setTag(com.wlj.base.R.id.tag_first, item);
                Glide.with(getActivity()).
                        load(item == null ? R.drawable.icon_41_renzheng : item.getUrl())
//                        .placeholder(R.drawable.icon_41_renzheng)
                        .crossFade(1000)
                        .into(imageView1);

                return imageView1;
            }
        };
    }

    private void initGridview() {
        mDatas = new ArrayList<>();
        mDatas.add(new GridItem(R.drawable.icon_35, "门店管理"));
        mDatas.add(new GridItem(R.drawable.icon_36, "商家核销"));
        mDatas.add(new GridItem(R.drawable.icon_37, "评论管理"));
        mDatas.add(new GridItem(R.drawable.icon_38, "订单管理"));
        mDatas.add(new GridItem(R.drawable.icon_39, "会员管理"));
        mDatas.add(new GridItem(R.drawable.icon_40, "积分管理"));

        gridviewAdapter = new CommonAdapter<GridItem>(getActivity(), mDatas, R.layout.item_home_grideview) {
            @Override
            public View getListItemview(ViewHolder viewHolder, View view, GridItem item, int position, ViewGroup parent) {
                viewHolder.setImageResource(R.id.itemhome_image, item.getImage());
                viewHolder.setText(R.id.itemhome_text, item.getText());
                return null;
            }
        };

        homeGridview.setAdapter(gridviewAdapter);

        homeGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0://门店管理
                        GoToHelp.go(getActivity(), ShoppingManagerActivity.class);
                        break;
                    case 1://商家核销
                        renZhengHelp.VerifyRenZhengIng(authenticate_status, new RenZhengHelp.NoRenZhengIng() {
                            @Override
                            public void renZhenged() {
                                //核销
                                Bundle bundle = new Bundle();
                                bundle.putString(MipcaActivityCapture.TITLE, "商家核销");
                                GoToHelp.goResult(getActivity(), CodeScanActivity.class, CODESCANRQ, bundle);
                            }
                        });

                        break;
                    case 2://评论管理
                        renZhengHelp.VerifyRenZhengIng(authenticate_status, new RenZhengHelp.NoRenZhengIng() {
                            @Override
                            public void renZhenged() {
                                GoToHelp.go(getActivity(), CommentActivity.class);
                            }
                        });
                        break;
                    case 3://订单管理
                        renZhengHelp.VerifyRenZhengIng(authenticate_status, new RenZhengHelp.NoRenZhengIng() {
                            @Override
                            public void renZhenged() {
                                GoToHelp.go(getActivity(), OrderListActivity.class);
                            }
                        });

                        break;
                    case 4://会员管理
                        renZhengHelp.VerifyRenZhengIng(authenticate_status, new RenZhengHelp.NoRenZhengIng() {
                            @Override
                            public void renZhenged() {
                                GoToHelp.go(getActivity(), UserActivity.class);
                            }
                        });
                        break;
                    case 5://积分管理
                        renZhengHelp.VerifyRenZhengIng(authenticate_status, new RenZhengHelp.NoRenZhengIng() {
                            @Override
                            public void renZhenged() {
                                GoToHelp.go(getActivity(), ScoreActivity.class);
                            }
                        });
                        break;

                }
            }
        });

    }

    @OnClick({R.id.title_left, R.id.title_right, R.id.home_renzhengimage})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_left:
                break;
            case R.id.title_right:
                break;
            case R.id.home_renzhengimage:

                renZhengHelp.VerifyRenZhengIng(authenticate_status, new RenZhengHelp.NoRenZhengIng() {
                    @Override
                    public void renZhenged() {
                    }
                });
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != getActivity().RESULT_OK) return;
        if (CODESCANRQ == requestCode && data != null) {
            String stringExtra = data.getStringExtra(MipcaActivityCapture.RESULTSTRING);

        }

    }

    /**
     * This method will be invoked when the current page is scrolled, either as part
     * of a programmatically initiated smooth scroll or a user initiated touch scroll.
     *
     * @param position             Position index of the first page currently being displayed.
     *                             Page position+1 will be visible if positionOffset is nonzero.
     * @param positionOffset       Value from [0, 1) indicating the offset from the page at position.
     * @param positionOffsetPixels Value in pixels indicating the offset from position.
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    /**
     * This method will be invoked when a new page becomes selected. Animation is not
     * necessarily complete.
     *
     * @param position Position index of the new selected page.
     */
    @Override
    public void onPageSelected(int position) {

        int size = list.size();
        String number = "0" + "/" + "0";
        if (size != 0) {
            int m = position % size;
            number = size + "/" + (m+1);
        }
        imageNumber.setText(number);
    }

    /**
     * Called when the scroll state changes. Useful for discovering when the user
     * begins dragging, when the pager is automatically settling to the current page,
     * or when it is fully stopped/idle.
     *
     * @param state The new scroll state.
     * @see ViewPager#SCROLL_STATE_IDLE
     * @see ViewPager#SCROLL_STATE_DRAGGING
     * @see ViewPager#SCROLL_STATE_SETTLING
     */
    @Override
    public void onPageScrollStateChanged(int state) {

    }

    class GridItem {

        private int image;
        private String text;

        public GridItem(int image, String text) {
            this.image = image;
            this.text = text;
        }

        public int getImage() {
            return image;
        }

        public String getText() {
            return text;
        }
    }


}
