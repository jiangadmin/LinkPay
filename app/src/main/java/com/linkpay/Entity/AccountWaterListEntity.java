package com.linkpay.Entity;

import com.linkpay.Activity.BaseActivity;

import java.util.ArrayList;

/**
 * Created by jiang
 * on 16/8/10
 * Email: www.fangmu@qq.com
 * Phone：186 6120 1018
 * Purpose:交易明细
 * update:
 */
public class AccountWaterListEntity extends BaseResult {

    /**
     * id : 621
     * saleruMoney : -0.01
     * saleruTime : 2016-08-10 05:08:44
     * saleruAfterMoney : 3377.14
     * saleruType : 1
     */


    private ArrayList<AccountWearBean> saleruList;

    public ArrayList<AccountWearBean> getSaleruList() {
        return saleruList;
    }

    public void setSaleruList(ArrayList<AccountWearBean> saleruList) {
        this.saleruList = saleruList;
    }

    public static class SaleruListBean {
        private int id;
        private String saleruMoney;
        private String saleruTime;
        private String saleruAfterMoney;
        private int saleruType;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getSaleruMoney() {
            return saleruMoney;
        }

        public void setSaleruMoney(String saleruMoney) {
            this.saleruMoney = saleruMoney;
        }

        public String getSaleruTime() {
            return saleruTime;
        }

        public void setSaleruTime(String saleruTime) {
            this.saleruTime = saleruTime;
        }

        public String getSaleruAfterMoney() {
            return saleruAfterMoney;
        }

        public void setSaleruAfterMoney(String saleruAfterMoney) {
            this.saleruAfterMoney = saleruAfterMoney;
        }

        public int getSaleruType() {
            return saleruType;
        }

        public void setSaleruType(int saleruType) {
            this.saleruType = saleruType;
        }
    }
}
