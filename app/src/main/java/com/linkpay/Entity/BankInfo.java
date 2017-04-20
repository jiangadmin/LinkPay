package com.linkpay.Entity;

/**
 * Created by jiang
 * on 16/3/10
 * Email: www.fangmu@qq.com
 * Phone：186 6120 1018
 * Purpose:联行信息存储
 * update:
 */
public class BankInfo {
    private int id;
    private int bankId;
    private String name;
    private String bankName;
    private String branch;
    private String linkno;

    public int getBankId() {
        return bankId;
    }

    public void setBankId(int bankId) {
        this.bankId = bankId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getLinkno() {
        return linkno;
    }

    public void setLinkno(String linkno) {
        this.linkno = linkno;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
