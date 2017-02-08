package com.dw.applebuy.ui.home;

import android.net.Uri;
import android.os.Bundle;
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

import com.dw.applebuy.R;
import com.dw.applebuy.been.Info;
import com.dw.applebuy.ui.MainActivity;
import com.dw.applebuy.ui.home.ordermanage.OrderListActivity;
import com.dw.applebuy.ui.home.renzheng.RenZhengActivity;
import com.dw.applebuy.ui.home.renzheng.p.InfoUtil;
import com.dw.applebuy.ui.home.scoremanage.ScoreActivity;
import com.dw.applebuy.ui.home.shoppingmanage.ShoppingManagerActivity;
import com.dw.applebuy.ui.home.usermanage.UserActivity;
import com.wlj.base.adapter.CommonAdapter;
import com.wlj.base.adapter.ViewHolder;
import com.wlj.base.util.GoToHelp;

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
    ViewPager homeYirenzhengViewpager;
    @BindView(R.id.home_yirenzheng_number)
    TextView homeYirenzhengNumber;
    @BindView(R.id.home_yirenzheng_layout)
    FrameLayout homeYirenzhengLayout;
    @BindView(R.id.home_lijirenzheng_layout)
    RelativeLayout homeLijirenzhengLayout;
    @BindView(R.id.home_gridview)
    GridView homeGridview;
    @BindView(R.id.home_renzhengimage)
    ImageView renzhengimage;

    private List<GridItem> mDatas;
    private CommonAdapter<GridItem> gridviewAdapter;

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

        InfoUtil.getInstall().getInfoFromWeb(getActivity(),new InfoUtil.InfoBack() {
            @Override
            public void back(Info info) {
                //认证状态(1:未认证  2:认证中  3:已认证  4:认证失败)
                String authenticate_status = info.getAuthenticate_status();
                switch (authenticate_status) {
                    case "1":
                        homeYirenzhengLayout.setVisibility(View.VISIBLE);
                        renzhengimage.setImageResource(R.drawable.icon_lijirenzheng);

                        homeYirenzhengNumber.setVisibility(View.GONE);
                        break;
                    case "2":

                        break;
                    case "3":
                        homeYirenzhengLayout.setVisibility(View.GONE);
                        homeYirenzhengNumber.setVisibility(View.VISIBLE);

                        break;
                    case "4":

                        break;
                }
            }
        });
    }

    private void initView() {

        initGridview();
    }

    private void initGridview() {
        mDatas = new ArrayList<>();
        mDatas.add(new GridItem(R.drawable.icon_35, "门店管理"));
        mDatas.add(new GridItem(R.drawable.icon_36, "消费记录"));
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
                        GoToHelp.go(getActivity(),ShoppingManagerActivity.class);
                        break;
                    case 1://消费管理
                        break;
                    case 2://评论管理
                        break;
                    case 3://订单管理
                        GoToHelp.go(getActivity(),OrderListActivity.class);
                        break;
                    case 4://会员管理
                        GoToHelp.go(getActivity(),UserActivity.class);
                        break;
                    case 5://积分管理
                        GoToHelp.go(getActivity(),ScoreActivity.class);
                        break;

                }
            }
        });

    }


    @OnClick({R.id.title_left, R.id.title_right,R.id.home_renzhengimage})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_left:
                break;
            case R.id.title_right:
                break;
            case R.id.home_renzhengimage:
                GoToHelp.go(getActivity(), RenZhengActivity.class);
                break;
        }
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
