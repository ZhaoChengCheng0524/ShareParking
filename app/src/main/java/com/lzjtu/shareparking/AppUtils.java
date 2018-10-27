package com.lzjtu.shareparking;


import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/8/2
 *     desc  : App相关工具类
 * </pre>
 */
public class AppUtils {
    private AppUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }


    /**
     * 获取应用的 SHA1 值， 可据此判断高德，百度地图 key 是否正确
     * @param context 上下文
     * @return 应用的 SHA1 字符串, 比如： 53:FD:54:DC:19:0F:11:AC:B5:22:9E:F1:1A:68:88:1B:8B:E8:54:42
     */
    public static String getSHA1(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuilder hexString = new StringBuilder();
            for (byte aPublicKey : publicKey) {
                String appendString = Integer.toHexString(0xFF & aPublicKey).toUpperCase();
                if (appendString.length() == 1)
                    hexString.append("0");
                hexString.append(appendString);
                hexString.append(":");
            }
            String result = hexString.toString();
            return result.substring(0, result.length() - 1);
        } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}