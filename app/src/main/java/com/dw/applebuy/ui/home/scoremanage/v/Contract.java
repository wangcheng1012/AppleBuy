package com.dw.applebuy.ui.home.scoremanage.v;

import com.dw.applebuy.been.RechageScoreOrder;
import com.dw.applebuy.been.ScorePackage;
import com.rxmvp.basemvp.BaseView;

import java.math.BigDecimal;
import java.util.List;

/**
 *
 */
public interface Contract {

    /**
     * 充值积分
     */
    interface ReChangeScoreView extends BaseView {

        void scorePackageBack(List<ScorePackage.PackageBean> scorePackages);

        void jisuanBack(BigDecimal divide, String s);

        /**
         * 确认充值 返回
         * @param rechageScoreOrder
         */
        void sureRechageBack(RechageScoreOrder rechageScoreOrder);
    }



}
