package com.dw.applebuy.been;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import cn.trinea.android.common.annotation.NotProguard;

/**
 *  积分套餐
 */
@NotProguard
public class ScorePackage {

    /**
     * package : [{"id":"5","description":"优惠25","integral":"50000","price":"475.00"},{"id":"6","description":"优惠50","integral":"100000","price":"950.00"}]
     * integral_money : {"integral_money":"100"}
     */

    private IntegralMoneyBean integral_money;
    @SerializedName("package")
    private List<PackageBean> packageX;

    public IntegralMoneyBean getIntegral_money() {
        return integral_money;
    }

    public void setIntegral_money(IntegralMoneyBean integral_money) {
        this.integral_money = integral_money;
    }

    public List<PackageBean> getPackageX() {
        return packageX;
    }

    public void setPackageX(List<PackageBean> packageX) {
        this.packageX = packageX;
    }
    @NotProguard
    public static class IntegralMoneyBean {
        /**
         * integral_money : 100
         */

        private String integral_money;

        public String getIntegral_money() {
            return integral_money;
        }

        public void setIntegral_money(String integral_money) {
            this.integral_money = integral_money;
        }
    }
    @NotProguard
    public static class PackageBean {
        /**
         * id : 5
         * description : 优惠25
         * integral : 50000
         * price : 475.00
         */

        private String id;
        private String description;
        private String integral;
        private String price;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getIntegral() {
            return integral;
        }

        public void setIntegral(String integral) {
            this.integral = integral;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }
}
