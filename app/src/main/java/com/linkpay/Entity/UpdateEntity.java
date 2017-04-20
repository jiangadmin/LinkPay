package com.linkpay.Entity;

/**
 * Created by jiang
 * on 16/10/12.
 * Email: jiangyaoyao@chinarb.cn
 * Phone：18605296932
 * Purpose:
 */
public class UpdateEntity extends BaseResult {


    /**
     * text : wo kai xin
     * url : http://192.168.1.102:8080/PhonePOSPInterface/Test.apk
     * version : 1
     * needUpdate : 1
     */

    private String text;        //更新描述
    private String url;         //更新地址
    private String version;     //服务器版本
    private int needUpdate;     //强制更新

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getNeedUpdate() {
        return needUpdate;
    }

    public void setNeedUpdate(int needUpdate) {
        this.needUpdate = needUpdate;
    }
}
