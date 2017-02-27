package com.dw.applebuy.util;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.widget.LinearLayout;

import com.dw.applebuy.R;
import com.wlj.base.util.DpAndPx;

/**
 * 不好分类的工具方法
 */
public class OtherTool {


    /**
     * 竖直 分割线
     *
     * @param tablayout
     */
    public static void splitLine(Context mContext, TabLayout tablayout) {
        //分割线
        LinearLayout child = (LinearLayout) tablayout.getChildAt(0);
        child.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        child.setDividerDrawable( mContext.getResources().getDrawable(R.drawable.shape_divider));
        child.setDividerPadding(DpAndPx.dpToPx(mContext, 15));

    }

}
