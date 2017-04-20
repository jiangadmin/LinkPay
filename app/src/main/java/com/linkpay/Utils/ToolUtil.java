package com.linkpay.Utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.ThumbnailUtils;
import android.net.ConnectivityManager;
import android.os.Environment;
import android.view.Window;
import android.view.WindowManager;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ToolUtil {

    private static final String TAG = "ToolUtil";

    public static boolean isEmpty(String s) {
        return s == null ? true : (s.trim().equals("") ? true : s.trim().equals("null"));
    }

    //检查网络是否正常 [功能描述].
    public static boolean checkNetwork(Context context) {
        boolean flag1 = false;
        ConnectivityManager cwjManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cwjManager.getActiveNetworkInfo() != null) {
            flag1 = cwjManager.getActiveNetworkInfo().isAvailable();
        }
        return flag1;
    }

    /**
     * 获得状态栏的高度
     *
     * @param context
     * @return
     */
    public static int getStatusHeight(Context context) {

        int statusHeight = -1;
        try {
            Class clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    /**
     * 获得导航栏的高度
     *
     * @param context
     * @return
     */
    public static int getNavigationHeight(Context context) {

        int statusHeight = -1;
        try {
            Class clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("navigation_bar_height").get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }


    /**
     * 分辨率从 dp 的单位 转成为 px(像素)
     * @param context
     * @param dpValue dp值
     * @return px像素
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    // 判断字符是否包含了字母和数字
    public static boolean isNormal(String number) {
        boolean isOk = false;
        String regexNum = ".*[0-9]+.*";
        Pattern pattern = Pattern.compile(regexNum);
        Matcher isNum = pattern.matcher(number);
        String regexEn = ".*[a-zA-Z]+.*";
        Pattern patternEn = Pattern.compile(regexEn);
        Matcher isEnglish = patternEn.matcher(number);
        if (isNum.matches() && isEnglish.matches()) {
            isOk = true;
        } else {

            isOk = false;
        }

        return isOk;
    }

    //判断手机号码是否正确
    public static boolean isMobileNO(String mobiles) {
//        Pattern p = Pattern.compile("^0{0,1}(13[0-9]|144|145|15[0-9]|166|170|17[6-8]|18[0-9])[0-9]{8}$");
        Pattern p = Pattern.compile("^0{0,1}(13[0-9]|144|145|15[0-9]|166|17[6-8]|18[0-9])[0-9]{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    //验证邮箱
    public static Boolean checkEmail(String email) {
        String check = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(email);
        boolean isMatched = matcher.matches();

        return isMatched;
    }

    String regex = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";// 精确验证手机号码

    //验证IP地址
    public static boolean isIP(String str) {
        String num = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";
        String regex = "^" + num + "\\." + num + "\\." + num + "\\." + num
                + "$";
        return match(regex, str);
    }

    //验证网址Url
    public static boolean IsUrl(String str) {
        String regex = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";
        return match(regex, str);
    }

    //验证电话号码
    public static boolean IsTelephone(String str) {

        String regex = "^(\\d{3,4}-)?\\d{6,8}$";
        // String regex = "^\\d{1,18}$";
        return match(regex, str);
    }

    //验证输入密码条件(字符与数据同时出现)
    public static boolean IsPassword(String str) {
        String regex = "[A-Za-z]+[0-9]";
        return match(regex, str);
    }

    //验证输入密码长度 (6-18位)
    public static boolean IsPasswLength(String str) {
        String regex = "^\\d{6,18}$";
        return match(regex, str);
    }

    //验证输入邮政编号
    public static boolean IsPostalcode(String str) {
        String regex = "^\\d{6}$";
        return match(regex, str);
    }

    //验证输入手机号码
    public static boolean IsHandset(String str) {
        String regex = "^[0,1]+[3,5]+\\d{9}$";
        // String regex = "^\\d{1,18}$";

        return match(regex, str);
    }


    //验证输入身份证号
    public static boolean IsIDcard(String str) {
        String regex = "(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X|x)$)";
        return match(regex, str);
    }
    //验证输入两位小数
    public static boolean IsDecimal(String str) {
        String regex = "^[0-9]+(.[0-9]{2})?$";
        return match(regex, str);
    }

    // 验证输入一年的12个月
    public static boolean IsMonth(String str) {
        String regex = "^(0?[[1-9]|1[0-2])$";
        return match(regex, str);
    }

    //验证输入一个月的31天
    public static boolean IsDay(String str) {
        String regex = "^((0?[1-9])|((1|2)[0-9])|30|31)$";
        return match(regex, str);
    }

    //验证日期时间
    public static boolean isDate(String str) {
        String regex = "^((((1[6-9]|[2-9]\\d)\\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\\d|3[01]))|(((1[6-9]|[2-9]\\d)\\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\\d|30))|(((1[6-9]|[2-9]\\d)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-8]))|(((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-)) (20|21|22|23|[0-1]?\\d):[0-5]?\\d:[0-5]?\\d$";
        return match(regex, str);
    }

    //验证数字输入
    public static boolean IsNumber(String str) {
        String regex = "^[0-9]*$";
        return match(regex, str);
    }

    //验证非零的正整数
    public static boolean IsIntNumber(String str) {
        String regex = "^\\+?[1-9][0-9]*$";
        return match(regex, str);
    }

    //验证大写字母
    public static boolean IsUpChar(String str) {
        String regex = "^[A-Z]+$";
        return match(regex, str);
    }

    //验证小写字母
    public static boolean IsLowChar(String str) {
        String regex = "^[a-z]+$";
        return match(regex, str);
    }

    // 验证验证输入字母
    public static boolean IsLetter(String str) {
        String regex = "^[A-Za-z]+$";
        return match(regex, str);
    }

    // 验证验证输入汉字
    public static boolean IsChinese(String str) {
        String regex = "^[\u4e00-\u9fa5],{0,}$";
        return match(regex, str);
    }

    //验证验证输入字符串
    public static boolean IsLength(String str) {
        String regex = "^.{8,}$";
        return match(regex, str);
    }

    private static boolean match(String regex, String str) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    //检查字符串是否含有HTML标签
    public static boolean checkHtmlTag(String str) {
        String regex = "^[a-zA-Z0-9],{0,}$";
        return match(regex, str);
    }

    //检查输入的数据中是否有特殊字符
    public static boolean hasCrossScriptRisk(String qString, String regx) {
        if (qString != null) {
            qString = qString.trim();
            Pattern p = Pattern.compile(regx, Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(qString);
            return m.find();
        }
        return false;
    }

    //检查输入的数据中是否有特殊字符
    public static boolean checkString(String qString) {
        String regx = "!|！|@|◎|#|＃|(\\$)|￥|%|％|(\\^)|……|(\\&)|※|(\\*)|×|(\\()|（|(\\))|）|_|——|(\\+)|＋|(\\|)|§ ";
        return hasCrossScriptRisk(qString, regx);
    }

    // 检查校验位
    private static boolean checkIDParityBit(String certiCode) {
        boolean flag = false;
        if (certiCode == null || "".equals(certiCode))
            return false;
        int ai[] = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1};
        if (certiCode.length() == 18) {
            int i = 0;
            for (int k = 0; k < 18; k++) {
                char c = certiCode.charAt(k);
                int j;
                if (c == 'X')
                    j = 10;
                else if (c <= '9' && c >= '0')
                    j = c - 48;
                else
                    return flag;
                i += j * ai[k];
            }

            if (i % 11 == 1)
                flag = true;
        }
        return flag;
    }

    //检查日期格式
    private static boolean checkDate(String year, String month, String day) {
        SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMMdd");
        try {
            String s3 = year + month + day;
            simpledateformat.setLenient(false);
            simpledateformat.parse(s3);
        } catch (java.text.ParseException parseexception) {
            return false;
        }
        return true;
    }

    //校验身份证
    private static int checkCertiCode(String certiCode) {
        try {
            if (certiCode == null || certiCode.length() != 15
                    && certiCode.length() != 18)
                return 1;
            String s1;
            String s2;
            String s3;

            if (certiCode.length() == 15) {

                if (!checkFigure(certiCode)) {
                    return 5;
                }

                s1 = "19" + certiCode.substring(6, 8);
                s2 = certiCode.substring(8, 10);
                s3 = certiCode.substring(10, 12);

                if (!checkDate(s1, s2, s3))
                    return 2;
            }

            if (certiCode.length() == 18) {
                if (!checkFigure(certiCode.substring(0, 17))) {
                    return 5;
                }

                s1 = certiCode.substring(6, 10);
                s2 = certiCode.substring(10, 12);
                s3 = certiCode.substring(12, 14);

                if (!checkDate(s1, s2, s3))
                    return 2;
                if (!checkIDParityBit(certiCode))
                    return 3;
            }
        } catch (Exception exception) {

            return 4;
        }
        return 0;
    }




    // 检查字符串是否全为数字
    private static boolean checkFigure(String certiCode) {
        try {
            Long.parseLong(certiCode);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static String getCustomDateAndTime() {
        String current = "";
        try {
            long curTime = System.currentTimeMillis();
            SimpleDateFormat sf = new SimpleDateFormat("yyMMddHHmmssSSS");
            Calendar mCalendar = Calendar.getInstance();
            mCalendar.setTimeInMillis(curTime);
            current = sf.format(mCalendar.getTime());
        } catch (Exception e) {

        }
        return current;
    }

    public static String getCustomDate_Time() {
        String current = "";
        try {
            long curTime = System.currentTimeMillis();
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Calendar mCalendar = Calendar.getInstance();
            mCalendar.setTimeInMillis(curTime);
            current = sf.format(mCalendar.getTime());
        } catch (Exception e) {

        }
        return current;
    }


    // 格式化数字为千分位显示；
    public static String fmtMicrometer(String text) {
        DecimalFormat df = null;
        if (text.indexOf(".") > 0) {
            if (text.length() - text.indexOf(".") - 1 == 0) {
                df = new DecimalFormat("###,##0.");
            } else if (text.length() - text.indexOf(".") - 1 == 1) {
                df = new DecimalFormat("###,##0.0");
            } else {
                df = new DecimalFormat("###,##0.00");
            }
        } else {
            df = new DecimalFormat("###,##0");
        }
        double number = 0.0;
        try {
            number = Double.parseDouble(text);
        } catch (Exception e) {
            number = 0.0;
        }
        return df.format(number);
    }

    // 获取AndroidManifest.xml中android:versionName
    public static String getSoftwareVersion(Context context) {
        String packageName = "com.dd.community";
        String sv = "";
        try {
            PackageInfo pkinfo = context.getPackageManager().getPackageInfo(
                    packageName, PackageManager.GET_CONFIGURATIONS);
            sv = pkinfo.versionName;
            return sv;
        } catch (NameNotFoundException e) {

        }
        return "";
    }

    //检测是否有sdcard
    public static boolean checkSdcard() {

        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            // sd card 可用
            return true;
        } else {
            // 当前不可用
            return false;
        }

    }

    // 转换图片成圆形
    public static Bitmap toRoundBitmap(Bitmap bitmap, int newWidth, int newHeight) {
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, 80, 80, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        if (newHeight == height && newWidth == width) {

            // 强制使新旧图片分辨率不一样，防止temp和bitmap是同一对象

            height = height - 1;

        }
        float roundPx;
        float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
        if (width <= height) {
            roundPx = width / 2;
            top = 0;
            bottom = width;
            left = 0;
            right = width;
            height = width;
            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        } else {
            roundPx = height / 2;
            float clip = (width - height) / 2;
            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;
            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }

        Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect src = new Rect((int) left, (int) top, (int) right,
                (int) bottom);
        final Rect dst = new Rect((int) dst_left, (int) dst_top,
                (int) dst_right, (int) dst_bottom);
        final RectF rectF = new RectF(dst);

        paint.setAntiAlias(true);

        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, src, dst, paint);
        return output;
    }

    // 邮箱验证
    public static boolean isEmail(String strEmail) {
        String strPattern = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(strEmail);
        if (m.matches()) {
            return true;
        } else {
            return false;
        }
    }

    //计算文件夹大小
    public static long getDirSize(File dir) {
        if (dir == null) {
            return 0;
        }
        if (!dir.isDirectory()) {
            return 0;
        }
        long dirSize = 0;
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                dirSize += file.length();
            } else if (file.isDirectory()) {
                dirSize += file.length();
                dirSize += getDirSize(file); // 如果遇到目录则通过递归调用继续统计
            }
        }
        return dirSize;
    }

    // param folderPath 文件夹完整绝对路径
    public static void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); // 删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath.toString();
            File myFilePath = new File(filePath);
            myFilePath.delete(); // 删除空文件夹
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //删除所有
    public static boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
                delFolder(path + "/" + tempList[i]);// 再删除空文件夹
                flag = true;
            }
        }
        return flag;
    }

    public static String readInputStream(InputStream inputStream, String encoding) {
        if (inputStream == null) {
            return null;
        }

        String str = null;
        try {
            int length = 0;
            byte[] bytes = new byte[1024];
            StringBuffer buffer = new StringBuffer();
            while ((length = inputStream.read(bytes, 0, bytes.length)) != -1) {
                String read = new String(bytes, 0, length, "ISO_8859_1");
                buffer.append(read);
            }
            byte[] allBytes = buffer.toString().getBytes("ISO_8859_1");
            str = new String(allBytes, encoding);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return str;
    }


}
