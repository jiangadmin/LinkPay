package com.linkpay.Entity;

/**
 * Created by jiang
 * on 16/8/11
 * Email: www.fangmu@qq.com
 * Phone：186 6120 1018
 * Purpose:资金明细实体类
 * update:
 */
public class AccountWearBean {

    public AccountWearBean() {
    }

    public AccountWearBean(int id, String saleruMoney, String saleruTime, String saleruAfterMoney, int saleruType) {
        this.id = id;
        this.saleruMoney = saleruMoney;
        this.saleruTime = saleruTime;
        this.saleruAfterMoney = saleruAfterMoney;
        this.saleruType = saleruType;
    }

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
