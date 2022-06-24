package com.imooc.reader.service.utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @ClassName MD5utils
 * @Description TODO
 * @Author changYU
 * @Date 2021/10/17 17:53
 * @Version 1.0
 **/
public class MD5Utils {

    public static String md5Digest(String source, Integer salt) {
        char[] chars = source.toCharArray();
        //混淆原数据
        for (int i = 0; i < chars.length; i++) {
            chars[i] = (char) (chars[i] + salt);
        }


        String target = new String(chars);
        String md5Hex = DigestUtils.md5Hex(target);

        return md5Hex;
    }


}
