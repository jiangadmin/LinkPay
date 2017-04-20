package com.linkpay.Entity;

/**
 * Created by jiang
 * on 2017/1/19.
 * Email: www.fangmu@qq.com
 * Phone：186 6120 1018
 * Purpose:请求发送数据
 */
public class SendPayEntity {
    private String phone;
    private String cardnum;
    private String money;
    private String orderNum;
    private String smscode;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCardnum() {
        return cardnum;
    }

    public void setCardnum(String cardnum) {
        this.cardnum = cardnum;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getSmscode() {
        return smscode;
    }

    public void setSmscode(String smscode) {
        this.smscode = smscode;
    }
}
