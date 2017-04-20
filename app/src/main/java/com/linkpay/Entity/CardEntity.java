package com.linkpay.Entity;

import java.util.ArrayList;

/**
 * Created by jiang
 * on 2017/1/17.
 * Email: www.fangmu@qq.com
 * Phone：186 6120 1018
 * Purpose:信用卡列表
 */
public class CardEntity extends BaseResult {

    /**
     * resultCode : 0
     * resultList : [{"bankName":"银行名","sabcBankCard":"卡号","sabcPhoneNum":"手机号"},{"bankName":"银行名","sabcBankCard":"卡号","sabcPhoneNum":"手机号"}]
     */

    private ArrayList<ResultListBean> resultList;


    public ArrayList<ResultListBean> getResultList() {
        return resultList;
    }

    public void setResultList(ArrayList<ResultListBean> resultList) {
        this.resultList = resultList;
    }

    public static class ResultListBean {


        /**
         * sabcId : 10
         * sabcState : 卡状态
         * sabcDate : null
         * sabcRemark : null
         * sabcSaruId : 商户ID
         * sabcAccountName : 商户姓名
         * sabcPhoneNum : 电话
         * sabcIdCard : 身份证号
         * sabcBankCard : 卡号
         * sabcCvn2 : CVN2
         * sabcExpDate : 有效期
         * sabcBankName : 银行名称
         * sabcBankCardType : 卡种
         */

        private int sabcId;
        private int sabcState;
        private String sabcDate;
        private String sabcRemark;
        private int sabcSaruId;
        private String sabcAccountName;
        private String sabcPhoneNum;
        private String sabcIdCard;
        private String sabcBankCard;
        private String sabcCvn2;
        private String sabcExpDate;
        private String sabcBankName;
        private String sabcBankCardType;

        public int getSabcId() {
            return sabcId;
        }

        public void setSabcId(int sabcId) {
            this.sabcId = sabcId;
        }

        public int getSabcState() {
            return sabcState;
        }

        public void setSabcState(int sabcState) {
            this.sabcState = sabcState;
        }

        public String getSabcDate() {
            return sabcDate;
        }

        public void setSabcDate(String sabcDate) {
            this.sabcDate = sabcDate;
        }

        public String getSabcRemark() {
            return sabcRemark;
        }

        public void setSabcRemark(String sabcRemark) {
            this.sabcRemark = sabcRemark;
        }

        public int getSabcSaruId() {
            return sabcSaruId;
        }

        public void setSabcSaruId(int sabcSaruId) {
            this.sabcSaruId = sabcSaruId;
        }

        public String getSabcAccountName() {
            return sabcAccountName;
        }

        public void setSabcAccountName(String sabcAccountName) {
            this.sabcAccountName = sabcAccountName;
        }

        public String getSabcPhoneNum() {
            return sabcPhoneNum;
        }

        public void setSabcPhoneNum(String sabcPhoneNum) {
            this.sabcPhoneNum = sabcPhoneNum;
        }

        public String getSabcIdCard() {
            return sabcIdCard;
        }

        public void setSabcIdCard(String sabcIdCard) {
            this.sabcIdCard = sabcIdCard;
        }

        public String getSabcBankCard() {
            return sabcBankCard;
        }

        public void setSabcBankCard(String sabcBankCard) {
            this.sabcBankCard = sabcBankCard;
        }

        public String getSabcCvn2() {
            return sabcCvn2;
        }

        public void setSabcCvn2(String sabcCvn2) {
            this.sabcCvn2 = sabcCvn2;
        }

        public String getSabcExpDate() {
            return sabcExpDate;
        }

        public void setSabcExpDate(String sabcExpDate) {
            this.sabcExpDate = sabcExpDate;
        }

        public String getSabcBankName() {
            return sabcBankName;
        }

        public void setSabcBankName(String sabcBankName) {
            this.sabcBankName = sabcBankName;
        }

        public String getSabcBankCardType() {
            return sabcBankCardType;
        }

        public void setSabcBankCardType(String sabcBankCardType) {
            this.sabcBankCardType = sabcBankCardType;
        }
    }
}
