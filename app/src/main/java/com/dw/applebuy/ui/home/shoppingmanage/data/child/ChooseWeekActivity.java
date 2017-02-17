package com.dw.applebuy.ui.home.shoppingmanage.data.child;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.dw.applebuy.R;
import com.dw.applebuy.been.Info;
import com.dw.applebuy.ui.Title1Fragment;
import com.dw.applebuy.ui.home.shoppingmanage.m.BusinessScope;
import com.wlj.base.decoration.DividerDecoration;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.dw.applebuy.ui.home.shoppingmanage.m.BusinessScope.BusinessWeek;

/**
 * 选择星期几
 */
public class ChooseWeekActivity extends AppCompatActivity implements MultiItemTypeAdapter.OnItemClickListener, Title1Fragment.TitleInterface {

    @BindView(R.id.choose_week_RecyclerView)
    RecyclerView recyclerView;
    private ArrayList<BusinessScope> datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_week);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {

        //数据准备
        Intent intent = getIntent();
        ArrayList<String> business_week_id = intent.getStringArrayListExtra("business_week_id");

        datas = new ArrayList<>(7);
        if(business_week_id == null){
            for (int i = 0; i < BusinessWeek.length; i++) {

                datas.add(new BusinessScope(BusinessWeek[i], false ));
            }
        }else {
            for (int i = 0; i < BusinessWeek.length; i++) {

                datas.add(new BusinessScope(BusinessWeek[i], business_week_id.contains(i + "")));
            }
        }
        //
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        CommonAdapter commonAdapter = new CommonAdapter<BusinessScope>(getApplicationContext(), R.layout.item_businessscope, datas) {
            @Override
            protected void convert(ViewHolder holder, BusinessScope item, int position) {

                holder.setText(R.id.item_businessscope_text, "每"+item.getName());
                View checkBox = holder.getView(R.id.item_businessscope_imageView);
                if (item.getSelected()){

                    checkBox.setVisibility(View.VISIBLE);
                }else{

                    checkBox.setVisibility(View.INVISIBLE);
                }
            }

        };

        recyclerView.setAdapter(commonAdapter);

        recyclerView.addItemDecoration(new DividerDecoration(getApplicationContext(),DividerDecoration.HORIZONTAL_LIST));

        commonAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
//        Object tag = view.getTag(R.id.tag_first);
        BusinessScope businessScope = datas.get(position);
        boolean selected = businessScope.getSelected();
        businessScope.setSelected(!selected);

        View byId = view.findViewById(R.id.item_businessscope_imageView);
        if (businessScope.getSelected()){

            byId.setVisibility(View.VISIBLE);
        }else{

            byId.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
        return false;
    }

    @Override
    public void setTitle(TextView title, TextView right) {
        title.setText("每周营业日");
    }

    @Override
    public void onBackPressed() {

        StringBuilder business_week = new StringBuilder();
        ArrayList<String> business_week_id = new ArrayList<>(7);

        for (int i = 0; i < datas.size(); i++) {
            BusinessScope scope = datas.get(i);
            if (scope.getSelected()) {
                business_week_id.add(i+"");
            }
        }
        Intent intent = new Intent();
        intent.putStringArrayListExtra( "business_week_id",business_week_id);
        setResult(RESULT_OK,intent);
        super.onBackPressed();

    }
}
