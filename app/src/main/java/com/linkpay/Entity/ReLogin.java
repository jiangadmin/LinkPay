package com.linkpay.Entity;

/**
 * Created by jiang
 * on 2016/10/17.
 * Email: jiangyaoyao@chinarb.cn
 * Phone：18605296932
 * Purpose:
 */
public class ReLogin extends BaseResult {


    /**
     * refuseMessage : 姜垚垚
     * phone : 18661201018
     * taiMaUrl : http://wx51.ling-pay.com/pal/
     * accountType : 9
     * procSn : 8600000040
     * plId : 9748
     * chiefName :
     */

    private String refuseMessage;
    private String phone;
    private String taiMaUrl;
    private int accountType;
    private int errorCount;
    private String procSn;
    private String plId;
    private String chiefName;
    private String saruLruid;

    public String getSaruLruid() {
        return saruLruid;
    }

    public void setSaruLruid(String saruLruid) {
        this.saruLruid = saruLruid;
    }

    /**
     * accountType :
     * resultCode : 0
     */


    public int getAccountType() {
        return accountType;
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }

    public int getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(int errorCount) {
        this.errorCount = errorCount;
    }

    /**
     * accountType :
     * resultCode : 0
     */

    public String getRefuseMessage() {
        return refuseMessage;
    }

    public void setRefuseMessage(String refuseMessage) {
        this.refuseMessage = refuseMessage;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTaiMaUrl() {
        return taiMaUrl;
    }

    public void setTaiMaUrl(String taiMaUrl) {
        this.taiMaUrl = taiMaUrl;
    }

    public String getProcSn() {
        return procSn;
    }

    public void setProcSn(String procSn) {
        this.procSn = procSn;
    }

    public String getPlId() {
        return plId;
    }

    public void setPlId(String plId) {
        this.plId = plId;
    }

    public String getChiefName() {
        return chiefName;
    }

    public void setChiefName(String chiefName) {
        this.chiefName = chiefName;
    }


}
