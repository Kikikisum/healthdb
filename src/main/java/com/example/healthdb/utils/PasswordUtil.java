package com.example.healthdb.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.BCrypt;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class PasswordUtil {

    /**
     * 对密码进行BCrypt加密
     * @param password
     * @return
     */
    public static String getPassword(String password)
    {
        return BCrypt.hashpw(password,BCrypt.gensalt());
    }

    /**
     * 验证密码
     *
     * @param inputPassword 输入的密码
     * @param hashedPassword 加密后的密码
     * @return 是否匹配
     */
    public static boolean checkPassword(String inputPassword, String hashedPassword) {
        return BCrypt.checkpw(inputPassword, hashedPassword);
    }

    /**
     * 检查密码是否符合要求
     * 6-16位数字和字母组合 不能纯数字或纯字母
     * @param newPassword 新密码
     * @return 是否符合规则
     */
    public static boolean checkPasswordRule(String newPassword) {
        if (StrUtil.isBlank(newPassword)) {
            return false;
        }
        if (newPassword.length() < 6 || newPassword.length() > 16) {
            return false;
        }
        return !newPassword.matches("^[0-9]*$") && !newPassword.matches("^[a-zA-Z]*$");
    }


    private static final String AES = "AES";

    // 加密方法
    public static String encrypt(String plainText) {
        // 使用Base64进行编码
        return Base64.getEncoder().encodeToString(plainText.getBytes());
    }

    // 解密方法
    public static String decrypt(String encryptedText) {
        if (encryptedText==null)
        {
            return null;
        }
        // 使用Base64进行解码
        byte[] decodedBytes = Base64.getDecoder().decode(encryptedText);
        return new String(decodedBytes);
    }
}
