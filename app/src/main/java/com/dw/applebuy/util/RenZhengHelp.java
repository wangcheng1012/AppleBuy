package com.dw.applebuy.util;

import android.app.Activity;
import android.os.Bundle;

import com.dw.applebuy.ui.home.renzheng.RenZhengActivity;
import com.dw.applebuy.ui.songjifen.GiveSuccessActivity;
import com.wlj.base.util.GoToHelp;

/**
 *  认证中
 */
public class RenZhengHelp {

    //认证状态(1:未认证  2:认证中  3:已认证  4:认证失败)
    public final static String  renzheng_no = "1";
    public final static String  renzheng_ing = "2";
    public final static String  renzheng_ed = "3";
    public final static String  renzheng_fail = "4";

    private static RenZhengHelp mRenZhengHelp;
    private Activity activity;

    private RenZhengHelp(Activity activity) {
        this.activity = activity;
    }

    public static RenZhengHelp getInstall(Activity activity) {
        if(mRenZhengHelp == null){
            mRenZhengHelp = new RenZhengHelp(activity);
        }
        return mRenZhengHelp;
    }

    public  void onClick( ){
        //认证中
        Bundle bundle = new Bundle();
        bundle.putString("from", GiveSuccessActivity.FROM_RENZHENG);
        GoToHelp.go(activity, GiveSuccessActivity.class,bundle);
    }

    /**
     *
     * @param authenticate_status 当前认证状态
     * @param noRenZhengIng 不在 认证中 状态的回调
     */
    public void VerifyRenZhengIng(String authenticate_status, NoRenZhengIng noRenZhengIng ){
        if(renzheng_ing.equals(authenticate_status)){
            onClick();
        }else if(renzheng_ed.equals(authenticate_status)){
            noRenZhengIng.renZhenged();
        }else{
            //去认证
            GoToHelp.go(activity, RenZhengActivity.class);
        }

    }

    public interface NoRenZhengIng{

        /**
         * 不在 认证中 状态
         */
        void renZhenged();
    }
}
