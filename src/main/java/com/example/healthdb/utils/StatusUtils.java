package com.example.healthdb.utils;

/**
 * 将status和权限进行转换
 */
public class StatusUtils {

    public static final String USER_ROLE="user";

    public static final String ADMIN_ROLE="admin";

    public static final String COMPANION="companion";

    public static String changeFromStatusToRole(Integer status)
    {
        if (status==0)
        {
            return USER_ROLE;
        }
        else if (status==1)
        {
            return ADMIN_ROLE;
        }
        else
        {
                return COMPANION;
        }
    }
}
