package net.den3.den3Account.Util;

import java.nio.charset.Charset;
import java.util.Base64;

public class Base64Util {
    private static Charset charset = Charset.forName("UTF-8");

    public static String encode(byte[] byteArray){
        return Base64.getEncoder().encodeToString(byteArray);
    }

    public static byte[] decode(String base64){
        return Base64.getDecoder().decode(base64);
    }

    public static String encodeString(String str){
        return Base64.getEncoder().encodeToString(str.getBytes(charset));
    }

    public static String decodeString(String sentence){
        return new String(Base64.getDecoder().decode(sentence),charset);
    }
}
