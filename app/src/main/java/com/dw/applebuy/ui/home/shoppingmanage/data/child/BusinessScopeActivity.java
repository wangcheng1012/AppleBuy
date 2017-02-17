package com.dw.applebuy.ui.home.shoppingmanage.data.child;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dw.applebuy.R;
import com.dw.applebuy.base.api.FactoryInters;
import com.dw.applebuy.base.ui.SWRVContract;
import com.dw.applebuy.ui.home.shoppingmanage.m.BusinessScope;
import com.dw.applebuy.ui.home.shoppingmanage.youhui.add.YouHuiTypeActivity;
import com.rxmvp.bean.HttpResult;
import com.wlj.base.decoration.DividerDecoration;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * 店铺管理 -》资料管理 -》经营范围
 */
public class BusinessScopeActivity extends YouHuiTypeActivity {

    public static final String RESULT_BusinessScope = "BusinessScope";
    private ArrayList<String> businessScopes;
    private List<BusinessScope> data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        businessScopes = getIntent().getStringArrayListExtra("businessScopes");

    }

    @Override
    protected void onCreateViewExtract(RecyclerView recyclerview, SwipeRefreshLayout swipeRefreshLayout) {
        recyclerview.addItemDecoration(new DividerDecoration(getApplicationContext(),DividerDecoration.HORIZONTAL_LIST));
    }

    protected SWRVContract.SWRVPresenterAdapter<BusinessScope> getMyPresenterAdapter() {
        return new SWRVContract.SWRVPresenterAdapter<BusinessScope>() {
            @Override
            public int getRecycerviewItemlayoutRes() {
                return R.layout.item_businessscope;
            }

            @Override
            public RecyclerView.LayoutManager getLayoutManager() {
                return new LinearLayoutManager(getApplicationContext());
            }

            @Override
            public void convert(ViewHolder viewHolder, BusinessScope item, int position) {
                viewHolder.setText(R.id.item_businessscope_text, item.getName());
                ImageView checkBox = viewHolder.getView(R.id.item_businessscope_imageView);
                if (item.getSelected()){

                    checkBox.setVisibility(View.VISIBLE);
                }else{

                    checkBox.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onItemLongClick(View view, RecyclerView.ViewHolder holder, int position, BusinessScope item) {

            }

            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position, BusinessScope item) {
                boolean selected = item.getSelected();
                item.setSelected(!selected);

                //
                View byId = view.findViewById(R.id.item_businessscope_imageView);

                if (item.getSelected()){

                    byId.setVisibility(View.VISIBLE);
                }else{

                    byId.setVisibility(View.GONE);
                }
            }

            @Override
            public View getEmptyView() {
                return null;
            }

            @Override
            public Observable<List<BusinessScope>> call(FactoryInters apiService, int curPageStart) {

                Observable<HttpResult<List<BusinessScope>>> allCategory = apiService.getAllCategory();

                Observable<List<BusinessScope>> observable = allCategory.map(new Func1<HttpResult<List<BusinessScope>>, List<BusinessScope>>() {
                    @Override
                    public List<BusinessScope> call(HttpResult<List<BusinessScope>> listHttpStateResult) {

                          data = listHttpStateResult.getData();

                        if (businessScopes == null)  return data;

                        for (int i = data.size() - 1; i >= 0; i--) {

                            BusinessScope businessScope = data.get(i);

                            if (businessScopes.indexOf(businessScope.getId()) != -1) {

                                businessScope.setSelected(true);
                            }
                        }
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
    public void setTitle(TextView title, TextView right) {

        title.setText("经营范围");
    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent();
        intent.putExtra("businessScopes",new ArrayList<>(data));
        setResult(RESULT_OK,intent);
        super.onBackPressed();
    }
}
