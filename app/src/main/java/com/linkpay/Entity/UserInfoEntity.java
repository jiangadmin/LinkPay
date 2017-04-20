package com.linkpay.Entity;

import com.linkpay.Utils.LogUtil;

import java.util.List;

/**
 * Created by jiang
 * on 2016/10/21.
 * Email: www.fangmu@qq.com
 * Phone：186 6120 1018
 * Purpose:用户信息
 */
public class UserInfoEntity {
    private static final String TAG = "UserInfoEntity";
    public static String name;          //姓名
    public static String idcard;        //身份证号
    public static String saruName;      //店铺名称
    public static String country;       //店铺地址
    public static String address;       //详细地址
    public static String bankCardNum;   //卡号
    public static String bankId;        //开户行总行id
    public static String bankName;      //开户行总行
    public static String cityId;        //开户行市id
    public static String cityName;      //开户行市
    public static String bankBranch;    //支行信息
    public static String bankBranchId;  //支行信息
    public static byte[] iDCardFront;   //身份证和银行卡正面
    public static byte[] iDCardBack;    //身份证和银行卡反面
    public static byte[] iDCardHand;    //手持身份证和银行卡半身照


    public static boolean Isnoll() {
        if (name == null ||
                idcard == null ||
                saruName == null ||
                country == null ||
                address == null ||
                bankCardNum == null ||
                bankId == null ||
                cityId == null ||
                bankBranch == null ||
                bankBranchId == null ||
                iDCardFront == null ||
                iDCardBack == null ||
                iDCardHand == null) {
            LogUtil.e(TAG, "姓名（name）：" + name
                    + "\n身份证号（idcard）：" + idcard
                    + "\n店铺名称(saruName)" + saruName
                    + "\n店铺地址(country)" + country
                    + "\n详细地址(address)" + address
                    + "\n卡号(bankCardNum)" + bankCardNum
                    + "\n开户行总行id(bankId)" + bankId
                    + "\n开户行总行(bankId)" + bankName
                    + "\n开户行市id(cityId)" + cityId
                    + "\n开户行市(cityName)" + cityName
                    + "\n支行信息(bankBranch)" + bankBranch
                    + "\n支行信息ID(bankBranchId)" + bankBranchId);
            return false;

        }
        return true;
    }

    public static List<BankInfo> bank;              //银行
    public static List<BankInfo> province;          //省会
    public static List<BankInfo> ctiy;              //城市
    public static List<BankInfo> region;            //县区
    public static List<BankInfo> xbank;             //联行

}
