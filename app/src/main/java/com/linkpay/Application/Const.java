package com.linkpay.Application;

/**
 * Created by jiang
 * on 16/10/12.
 * Email: jiangyaoyao@chinarb.cn
 * Phone：18605296932
 * Purpose:常量
 */
public class Const {
    public static boolean LOGSHOW;  //打印LOG
    public static boolean HTTPDES;  //通讯加密
    public static String URL;       //链接地址
    public static String IMAGE;     //广告地址
    public static String HUMAN;     //客服地址
    public static String BRAND;     //品牌号
    public static String HTTPKEY;  //通讯加密秘钥

    public static String PIID;      //商户号
    public static String SARULRUID; //ID
    public static String NAME;      //名字

    public static String LOGIN_PHONE;   //当前登录的手机号
    public static String PROC_SN;       //当前台码
    public static String PROC_SN_HTTP;  //当前台码地址


    /**
     * 用户状态
     * 1-审核通过
     * 2-废除
     * 3-未认证
     * 7-认证中
     * 9-审核否决
     * 12-审核否决
     */
    public static int ACCOUNT_TYPE;     //当前用户状态
    public final static int USERTYPE_NULL = -1;
    public final static int USERTYPE_OK = 1;
    public final static int USERTYPE_NO = 3;
    public final static int USERTYPE_ING = 7;
    public final static int USERTYPE_ERROR1 = 9;
    public final static int USERTYPE_ERROR2 = 12;
    public static String  USERTYPE_ERROR_MESSAGE ;


}
