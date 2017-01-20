package com.dw.applebuy.ui.home.shoppingmanage.data.child;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.dw.applebuy.R;
import com.dw.applebuy.base.api.FactoryInters;
import com.dw.applebuy.base.ui.SWRVContract;
import com.dw.applebuy.ui.home.shoppingmanage.m.BusinessScope;
import com.dw.applebuy.ui.home.shoppingmanage.youhui.add.YouHuiTypeActivity;
import com.rxmvp.bean.HttpStateResult;
import com.wlj.base.util.MathUtil;
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
    private  ArrayList<Integer> businessScopes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        businessScopes = getIntent().getIntegerArrayListExtra("businessScopes");
        if(businessScopes == null)businessScopes = new ArrayList<>();

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
                CheckBox checkBox = viewHolder.getView(R.id.item_businessscope_checkBox);
                boolean selected = item.getSelected();
                checkBox.setChecked(selected);
            }

            @Override
            public void onItemLongClick(View view, RecyclerView.ViewHolder holder, int position, BusinessScope item) {

            }

            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position, BusinessScope item) {
                boolean selected = item.getSelected();
                item.setSelected(!selected);
//                swrvFragment.getAdapter().notifyDataSetChanged();
            }

            @Override
            public View getEmptyView() {
                return null;
            }

            @Override
            public Observable<List<BusinessScope>> call(FactoryInters apiService, int curPageStart) {

                Observable<HttpStateResult<List<BusinessScope>>> allCategory = apiService.getAllCategory();

                Observable<List<BusinessScope>> observable = allCategory.map(new Func1<HttpStateResult<List<BusinessScope>>, List<BusinessScope>>() {
                    @Override
                    public List<BusinessScope> call(HttpStateResult<List<BusinessScope>> listHttpStateResult) {
                        List<BusinessScope> data = listHttpStateResult.getData();

                        for (int i = data.size() - 1; i >= 0; i--) {
                            BusinessScope businessScope = data.get(i);
                            if (businessScopes.indexOf(MathUtil.parseInteger(businessScope.getId())) != -1 ) {
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


}
