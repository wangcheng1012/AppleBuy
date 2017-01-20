package com.dw.applebuy.ui.home.shoppingmanage.data.child;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.dw.applebuy.R;
import com.dw.applebuy.ui.Title1Fragment;
import com.dw.applebuy.ui.home.shoppingmanage.m.BusinessScope;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 选择星期几
 */
public class ChooseWeekActivity extends AppCompatActivity implements MultiItemTypeAdapter.OnItemClickListener,Title1Fragment.TitleInterface {

    @BindView(R.id.choose_week_RecyclerView)
    RecyclerView recyclerView;
    private List<BusinessScope> datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_week);
        ButterKnife.bind(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        datas = new ArrayList<>(7);
        datas.add(new BusinessScope("每周日"));
        datas.add(new BusinessScope("每周一"));
        datas.add(new BusinessScope("每周二"));
        datas.add(new BusinessScope("每周三"));
        datas.add(new BusinessScope("每周四"));
        datas.add(new BusinessScope("每周五"));
        datas.add(new BusinessScope("每周六"));

        CommonAdapter commonAdapter = new CommonAdapter<BusinessScope>(getApplicationContext(), R.layout.item_businessscope, datas) {
            @Override
            protected void convert(ViewHolder holder, BusinessScope item, int position) {

                holder.setText(R.id.item_businessscope_text, item.getName());
                CheckBox checkBox = holder.getView(R.id.item_businessscope_checkBox);
                boolean selected = item.getSelected();
                checkBox.setChecked(selected);
            }

        };

        recyclerView.setAdapter(commonAdapter);

        commonAdapter.setOnItemClickListener(this );
    }

    @Override
    public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
//        Object tag = view.getTag(R.id.tag_first);
        BusinessScope businessScope = datas.get(position);
        boolean selected = businessScope.getSelected();
        businessScope.setSelected(!selected);
    }

    @Override
    public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
        return false;
    }

    @Override
    public void setTitle(TextView title, TextView right) {
        title.setText("每周营业日");
    }
}
