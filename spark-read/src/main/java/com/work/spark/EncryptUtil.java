package com.work.spark;

import java.math.BigInteger;
import java.security.MessageDigest;

public class EncryptUtil {

    public static String md5(String source) {
        try {
            byte[] bytesOfMessage = source.getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("MD5");
            return new BigInteger(1, md.digest(bytesOfMessage)).toString(16);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
