package com.example.healthdb.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConstantPropertiesUtil implements InitializingBean {

    @Value("${aliyun.oss.endpoint}")
    private String endpoint;

    @Value("${aliyun.oss.accessKeyId}")
    private String accessKeyId;

    @Value("${aliyun.oss.accessKeySecret}")
    private String accessKeySecret;

    @Value("${aliyun.oss.bucket-name}")
    private String bucketname;

    @Value("${aliyun.oss.urlPrefix}")
    private String urlPrefix;

    @Value("${aes.secret-key}")
    private String encryptStrSecretKey;

    public static String END_POINT;
    public static String ACCESS_KEY_ID;
    public static String ACCESS_KEY_SECRET;
    public static String BUCKET_NAME;

    public static String URL_PREFIX;

    public static String ENCRYPT_STR_SECRET_KEY;


    @Override
    public void afterPropertiesSet() {
        END_POINT = endpoint;
        ACCESS_KEY_ID = accessKeyId;
        ACCESS_KEY_SECRET = accessKeySecret;
        BUCKET_NAME = bucketname;
        URL_PREFIX = urlPrefix;
        ENCRYPT_STR_SECRET_KEY = encryptStrSecretKey;
    }
}