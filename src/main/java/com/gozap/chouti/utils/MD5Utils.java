package com.gozap.chouti.utils;

import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created with IntelliJ IDEA.
 * User: saint
 * Date: 13-5-3
 * Time: 下午3:26
 * To change this template use File | Settings | File Templates.
 */
public class MD5Utils {

    public static String encoderByMD5(String str) throws NoSuchAlgorithmException {
        MessageDigest md5=MessageDigest.getInstance("MD5");
        BASE64Encoder base64en = new BASE64Encoder();
        String newStr=base64en.encode(md5.digest(str.getBytes()));
        return newStr;
    }
}
