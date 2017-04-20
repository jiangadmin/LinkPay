package com.linkpay;

import com.linkpay.Application.Const;
import com.linkpay.Utils.DESedeTool;

import org.junit.Test;

import java.net.URLDecoder;
import java.net.URLEncoder;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void China(){
        String str = "resultCode";
        String stt = "resultCode";
        if (str==stt)
            System.out.println("相同");
        int num = str.length();//得到长度
        String laststr = str.substring(str.length()-1);//从倒数第二个开始截
        System.out.println("字符串中共有 "+num+" 个字符，最后一个字符是："+ laststr);
    }

    @Test
    public void 截取(){
        String str = "1234";
        System.out.println(str.substring(1,2));
    }

    @Test
    public void 解密(){
        String string = "7O/KJ01UQdVYgjPGYjj2igCujXBzXla6pINsjtF2U1QOP1ITHWAKUSDeaTus +NxHVK0/cUBu/x7+qNPCwboovuICyvBbwvhShrtxxTOGdjKK2RQRxTPgG3hB EfvMhCrNAzVwalzBo6s=";
        String key  = "12345678";
        System.out.println(DESedeTool.decryptDES(URLDecoder.decode(string), key));
        System.out.println(DESedeTool.decryptDES(string,key));

    }
    @Test
    public void 加密(){

        String string = "李惠";
        String key  = "12345678";
        System.out.println(URLEncoder.encode(DESedeTool.encryptDES(string, key).replaceAll(" ", "")));
    }

}