package com.dw.applebuy.ui.home.scoremanage.p;

import com.dw.applebuy.base.api.AppHttpMethods;
import com.dw.applebuy.been.RechageScoreOrder;
import com.dw.applebuy.been.ScorePackage;
import com.dw.applebuy.ui.home.scoremanage.v.Contract;
import com.rxmvp.basemvp.BasePresenter;
import com.wlj.base.util.MathUtil;

import java.math.BigDecimal;
import java.util.List;

import rx.Subscriber;

/**
 *  充值积分
 */
public class ReChangeScorePresenter extends BasePresenter<Contract.ReChangeScoreView> {
    private ScorePackage scorePackage;
    /**
     * 钱保留的小数位数
     */
    private int DecimalDigit = 2;

    public void getScorePackage() {

        Subscriber< ScorePackage>  ob = new Subscriber<ScorePackage>() {

            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                onErrorShow(e,"获取积分套餐失败");
            }

            @Override
            public void onNext(ScorePackage scorePackage) {
                ReChangeScorePresenter.this.scorePackage = scorePackage;
                if (mView != null){
                    mView.scorePackageBack(scorePackage.getPackageX());
                }
            }
        };

        AppHttpMethods.getInstance().getMctIntegralPackage(ob);

    }

    public void jisuan(String s) {
        if(scorePackage == null ){
            toastMessage("请先获取套餐数据");
            return;
        }
        ScorePackage.IntegralMoneyBean integralMoney = scorePackage.getIntegral_money();
        if(integralMoney != null){
            String money = integralMoney.getIntegral_money();

            BigDecimal divide = MathUtil.divide(s, money,DecimalDigit);

            if (mView != null ) {
                mView.jisuanBack(divide,s);
            }
        }

    }

    /**
     *
     * @param id 套餐充值时只传id，score传null
     * @param score  自定义时只传score，id传null
     */
    public void sureRechage(Integer id, Integer score) {
        mView.showLoading();
        Subscriber<RechageScoreOrder>  ob = new Subscriber<RechageScoreOrder>() {

            @Override
            public void onCompleted() {
                if (mView != null){
                    mView.hideLoading();
                }
            }

            @Override
            public void onError(Throwable e) {
                onErrorShow(e,"获取订单失败");
            }

            @Override
            public void onNext(RechageScoreOrder rechageScoreOrder) {
                if (mView != null){
                    mView.sureRechageBack(rechageScoreOrder);
                }
            }
        };
        AppHttpMethods.getInstance().rechargeIntegral(ob,id,score);
    }
}
