package com.dw.applebuy.ui.home.shoppingmanage.youhui.add;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.dw.applebuy.R;
import com.dw.applebuy.base.api.FactoryInters;
import com.dw.applebuy.base.ui.SWRVContract;
import com.dw.applebuy.base.ui.SWRVFragment;
import com.dw.applebuy.ui.Title1Fragment;
import com.dw.applebuy.ui.home.shoppingmanage.youhui.add.m.YouhuiQuanType;
import com.rxmvp.bean.HttpStateResult;
import com.wlj.base.util.AppConfig;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.functions.Func1;

/**
 * 优惠添加-》选择优惠类型
 */
public class YouHuiTypeActivity extends AppCompatActivity implements Title1Fragment.TitleInterface {

    public static final String RESULT_YouhuiQuanType = "YouhuiQuanType";
    @BindView(R.id.youhui_content)
    FrameLayout youhuiContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_you_hui_type);
        ButterKnife.bind(this);

        initView();

    }

    private void initView() {
        SWRVFragment<YouhuiQuanType> swrvFragment = new SWRVFragment<>();
        swrvFragment.setMyInterface(new SWRVFragment.SWRVInterface<YouhuiQuanType>() {
            @Override
            public void onCreateViewExtract(RecyclerView recyclerview, SwipeRefreshLayout swipeRefreshLayout) {

            }
            @Override
            public SWRVContract.SWRVPresenterAdapter<YouhuiQuanType> getPresenterAdapter() {
                return getMyPresenterAdapter();
            }
        });

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.youhui_content,swrvFragment);
        transaction.commitAllowingStateLoss();

    }

    private SWRVContract.SWRVPresenterAdapter<YouhuiQuanType> getMyPresenterAdapter() {
        return new SWRVContract.SWRVPresenterAdapter<YouhuiQuanType>() {
            @Override
            public int getRecycerviewItemlayoutRes() {
                return R.layout.item_youhuiquan;
            }

            @Override
            public RecyclerView.LayoutManager getLayoutManager() {
                return new LinearLayoutManager(getApplicationContext());
            }

            @Override
            public void convert(ViewHolder viewHolder, YouhuiQuanType item, int position) {
                viewHolder.setText(R.id.item_youhuiquan,item.getName());
            }

            @Override
            public void onItemLongClick(View view, RecyclerView.ViewHolder holder, int position, YouhuiQuanType item) {

            }

            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position, YouhuiQuanType item) {
                Intent intent = new Intent();
                intent.putExtra(RESULT_YouhuiQuanType,  item);
                setResult(RESULT_OK,intent);
                finish();
            }

            @Override
            public View getEmptyView() {
                return null;
            }

            @Override
            public Observable<List<YouhuiQuanType>> call(FactoryInters apiService) {
                Observable<HttpStateResult<List<YouhuiQuanType>>> couponCategory = apiService.getCouponCategory(AppConfig.getAppConfig().get(AppConfig.CONF_KEY));
                Observable<List<YouhuiQuanType>> observable = couponCategory.map(new Func1<HttpStateResult<List<YouhuiQuanType>>, List<YouhuiQuanType>>() {
                    @Override
                    public List<YouhuiQuanType> call(HttpStateResult<List<YouhuiQuanType>> listHttpStateResult) {
                        List<YouhuiQuanType> data = listHttpStateResult.getData();
                        return data;
                    }
                });
                return observable;
            }

            @Override
            public boolean needLoadMore() {
                return false;
            }
        };
    }

    @Override
    public void setTitle(TextView title) {
        title.setText("优惠类型");
    }

}
