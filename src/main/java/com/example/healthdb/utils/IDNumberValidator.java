package com.example.healthdb.utils;

public class IDNumberValidator {

    // 身份证号码长度
    private static final int ID_CARD_LENGTH = 18;

    // 身份证号码的每位加权因子
    private static final int[] WEIGHT_FACTOR = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };

    // 身份证号码的校验码
    private static final char[] VALIDATION_CODE = { '1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2' };

    /**
     * 校验身份证号码是否合法
     * @param idCard 身份证号码
     * @return true 合法，false 不合法
     */
    public static boolean isValid(String idCard) {
        // 长度校验
        if (idCard == null || idCard.length() != ID_CARD_LENGTH) {
            return false;
        }

        // 判断前17位是否为数字
        for (int i = 0; i < ID_CARD_LENGTH - 1; i++) {
            char ch = idCard.charAt(i);
            if (ch < '0' || ch > '9') {
                return false;
            }
        }

        // 计算校验位
        int sum = 0;
        for (int i = 0; i < ID_CARD_LENGTH - 1; i++) {
            int num = idCard.charAt(i) - '0';
            sum += num * WEIGHT_FACTOR[i];
        }

        // 取余得到校验码索引
        int remainder = sum % 11;
        char validationCode = VALIDATION_CODE[remainder];

        // 校验最后一位
        return idCard.charAt(ID_CARD_LENGTH - 1) == validationCode;
    }

    /**
     * 对身份证的除前三位和后4位的以外替换成*
     * @param identity
     * @return
     */
    public static String getEncryption(String identity)
    {
        if (identity==null)
        {
            return null;
        }
        int length = identity.length();
        StringBuilder maskedPhoneNumber = new StringBuilder(identity);
        for (int i = 3; i < length - 4; i++) {
            maskedPhoneNumber.setCharAt(i, '*');
        }

        return maskedPhoneNumber.toString();
    }

    /**
     * 对姓名加密
     * @param name
     * @return
     */
    public static String getName(String name)
    {
        if (name==null)
        {
            return null;
        }
        int length = name.length();
        StringBuilder maskName=new StringBuilder(name);
        if (length == 2)
        {
            maskName.setCharAt(1,'*');
        }
        else {
            for (int i=1;i < length -1;i++)
            {
                maskName.setCharAt(i,'*');
            }
        }
        return maskName.toString();
    }

    /**
     * 对电话进行加密
     * @param telephone
     * @return
     */
    public static String getNumber(String telephone)
    {
        if (telephone==null)
        {
            return null;
        }
        int length = telephone.length();
        StringBuilder maskedPhoneNumber = new StringBuilder(telephone);
        for (int i = 3; i < length - 2; i++) {
            maskedPhoneNumber.setCharAt(i, '*');
        }

        return maskedPhoneNumber.toString();
    }
}
