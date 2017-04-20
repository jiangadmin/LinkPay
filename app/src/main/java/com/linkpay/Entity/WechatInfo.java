package com.linkpay.Entity;

import java.util.List;

/**
 * Created by jiang
 * on 2017/1/12.
 * Email: www.fangmu@qq.com
 * Phone：186 6120 1018
 * Purpose:微信资料
 */
public class WechatInfo extends BaseResult {

    /**
     * openid : oIDnCwnk0LOvKnVfncBgTL5CSJN4
     * nickname : JUST DO IT
     * sex : 1
     * language : zh_CN
     * city : 南京
     * province : 江苏
     * country : 中国
     * headimgurl : http://wx.qlogo.cn/mmopen/DYAIOgq83eoQzKGhrbyjdxCdsyt0gBP4iaicQicLbFtExBd8pxghPPdV8FWxnaS07MicjShtzgoZDAaA6GVVXcEAGA/0
     * privilege : []
     * unionid : o5iHZwZY1IgjeIt0bXGT-jcYk8pw
     */

    private String openid;
    private String nickname;
    private int sex;
    private String language;
    private String city;
    private String province;
    private String country;
    private String headimgurl;
    private String unionid;
    private List<?> privilege;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public List<?> getPrivilege() {
        return privilege;
    }

    public void setPrivilege(List<?> privilege) {
        this.privilege = privilege;
    }
}
