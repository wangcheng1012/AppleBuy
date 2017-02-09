package com.dw.applebuy.ui.home;

import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dw.applebuy.R;
import com.dw.applebuy.been.Info;
import com.dw.applebuy.ui.MainActivity;
import com.dw.applebuy.ui.home.commentmanage.CommentActivity;
import com.dw.applebuy.ui.home.ordermanage.OrderListActivity;
import com.dw.applebuy.ui.home.renzheng.RenZhengActivity;
import com.dw.applebuy.ui.home.renzheng.p.InfoUtil;
import com.dw.applebuy.ui.home.scoremanage.ScoreActivity;
import com.dw.applebuy.ui.home.shoppingmanage.ShoppingManagerActivity;
import com.dw.applebuy.ui.home.usermanage.UserActivity;
import com.dw.applebuy.ui.songjifen.GiveSuccessActivity;
import com.dw.applebuy.util.RenZhengIngHelp;
import com.wlj.base.adapter.CommonAdapter;
import com.wlj.base.adapter.ViewHolder;
import com.wlj.base.util.AppContext;
import com.wlj.base.util.GoToHelp;
import com.wlj.base.util.UIHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeFragment extends Fragment {

    @BindView(R.id.title_left)
    TextView titleLeft;
    @BindView(R.id.title_title)
    TextView titleTitle;
    @BindView(R.id.title_right)
    TextView titleRight;
    @BindView(R.id.home_yirenzheng_viewpager)
    ViewPager viewPager;
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
    @BindView(R.id.home_renzheng_defimage)
    ImageView defimage;


    private List<GridItem> mDatas;
    private CommonAdapter<GridItem> gridviewAdapter;
    private String authenticate_status;
    private RenZhengIngHelp renZhengIngHelp;

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
                    case RenZhengIngHelp.renzheng_no:
                        homeLijirenzhengLayout.setVisibility(View.VISIBLE);
                        renzhengimage.setImageResource(R.drawable.icon_renzheng);
                        homeRenzhengtext.setText("请完成实名认证，保护商家权益");

                        imageNumber.setVisibility(View.GONE);
                        titleTitle.setText("尊敬的商户，你好！");
                        break;
                    case RenZhengIngHelp.renzheng_ing:
                        homeLijirenzhengLayout.setVisibility(View.VISIBLE);
                        renzhengimage.setImageResource(R.drawable.icon_renzhenging);
                        homeRenzhengtext.setText("你的认证已提交，请等待审核");

                        imageNumber.setVisibility(View.GONE);
                        titleTitle.setText("尊敬的商户，你好！");
                        break;
                    case RenZhengIngHelp.renzheng_ed:
                        homeLijirenzhengLayout.setVisibility(View.GONE);

                        imageNumber.setVisibility(View.GONE);
                        titleTitle.setText(info.getName());
                        break;
                    case RenZhengIngHelp.renzheng_fail:
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

    private void initView() {
          renZhengIngHelp = RenZhengIngHelp.getInstall(getActivity());
        initGridview();
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
                        renZhengIngHelp.VerifyRenZhengIng(authenticate_status, new RenZhengIngHelp.NoRenZhengIng() {
                            @Override
                            public void no() {
                                //核销

                            }
                        });

                        break;
                    case 2://评论管理
                        renZhengIngHelp.VerifyRenZhengIng(authenticate_status, new RenZhengIngHelp.NoRenZhengIng() {
                            @Override
                            public void no() {
                                GoToHelp.go(getActivity(), CommentActivity.class);
                            }
                        });

                        break;
                    case 3://订单管理
                        renZhengIngHelp.VerifyRenZhengIng(authenticate_status, new RenZhengIngHelp.NoRenZhengIng() {
                            @Override
                            public void no() {
                                GoToHelp.go(getActivity(), OrderListActivity.class);
                            }
                        });

                        break;
                    case 4://会员管理
                        renZhengIngHelp.VerifyRenZhengIng(authenticate_status, new RenZhengIngHelp.NoRenZhengIng() {
                            @Override
                            public void no() {
                                GoToHelp.go(getActivity(), UserActivity.class);
                            }
                        });
                        break;
                    case 5://积分管理
                        renZhengIngHelp.VerifyRenZhengIng(authenticate_status, new RenZhengIngHelp.NoRenZhengIng() {
                            @Override
                            public void no() {
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
                if (!AppContext.getAppContext().islogin()){
                    UIHelper.toastMessage(getContext(),"请先登录");
                    return;
                }

                renZhengIngHelp.VerifyRenZhengIng(authenticate_status, new RenZhengIngHelp.NoRenZhengIng() {
                    @Override
                    public void no() {
                        //认证
                        GoToHelp.go(getActivity(), RenZhengActivity.class);
                    }
                });

                break;
        }
    }

    /**
     *  认证中状态时，点击点击跳转
     */
    private void renZhengingGo(){
        //认证中
        Bundle bundle = new Bundle();
        bundle.putString("from",GiveSuccessActivity.FROM_RENZHENG);
        GoToHelp.go(getActivity(), GiveSuccessActivity.class,bundle);
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
