package com.linkpay.Entity;

/**
 * Created by jiangadmin
 * on 15/10/16.
 * Email: www.fangmu@qq.com
 * Phone：186 6120 1018
 * Purpose: 登录返回
 */
public class LoginInfoEntity extends BaseResult {


    //{"商户号":"100101000000041","termNum":"00000061","lordnum":"000097","iinvnum":"000001","saruId":41,"key62":"4d7ec312dc91c767be5b9b22771bd8e59f55470a7db79b8b78936af605aff6745ecec4c300009B86EE5C3345B5E6A861996B2F90FDA8015F33","zmk":"6D1B5E0F18655529B28D16DB845903BD","resultCode":"00","RenMark":2,"RenMessage":"16"}

    private String saruNum;     //商户号
    private String termNum;     //终端号
    private String lordnum;     //批次号
    private String iinvnum;     //流水号
    private String saruId;      //商户ID
    private String key62;
    /**
     * saruLoginPasswrod : 123456
     * saruInvcode : gh7Ft
     * saruLoginPhone : 13218061231
     * saruLruname : 手机客户端-个人
     */

    private String saruLoginPasswrod;
    private String saruInvcode;      //推荐码
    private String saruLoginPhone;
    private String saruLruname;


    @Override
    public String toString() {
        return "商户号:" + saruNum + "\n终端号:" + termNum + "\n批次号:" + lordnum + "\n流水号:" + iinvnum;
    }

    public String getSaruId() {
        return saruId;
    }

    public void setSaruId(String saruId) {
        this.saruId = saruId;
    }

    public String getKey62() {
        return key62;
    }

    public void setKey62(String key62) {
        this.key62 = key62;
    }

    public String getSaruNum() {
        return saruNum;
    }

    public void setSaruNum(String saruNum) {
        this.saruNum = saruNum;
    }

    public String getTermNum() {
        return termNum;
    }

    public void setTermNum(String termNum) {
        this.termNum = termNum;
    }

    public void setLordnum(String lordnum) {
        this.lordnum = lordnum;
    }

    public String getIinvnum() {
        return iinvnum;
    }

    public void setIinvnum(String iinvnum) {
        this.iinvnum = iinvnum;
    }

    public String getLordnum() {
        return lordnum;
    }

    public String getSaruLoginPasswrod() {
        return saruLoginPasswrod;
    }

    public void setSaruLoginPasswrod(String saruLoginPasswrod) {
        this.saruLoginPasswrod = saruLoginPasswrod;
    }

    public String getSaruInvcode() {
        return saruInvcode;
    }

    public void setSaruInvcode(String saruInvcode) {
        this.saruInvcode = saruInvcode;
    }

    public String getSaruLoginPhone() {
        return saruLoginPhone;
    }

    public void setSaruLoginPhone(String saruLoginPhone) {
        this.saruLoginPhone = saruLoginPhone;
    }

    public String getSaruLruname() {
        return saruLruname;
    }

    public void setSaruLruname(String saruLruname) {
        this.saruLruname = saruLruname;
    }
}
