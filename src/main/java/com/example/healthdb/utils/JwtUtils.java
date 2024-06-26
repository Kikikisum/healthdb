package com.example.healthdb.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Map;

public class JwtUtils {

    /**
     * 生成token  header.payload.singature
     */
    private static final String SING = "HEALDB_SINODB";

    public static final String ADMIN = "admin";

    public static String getToken(Map<String, String> map) {

        Calendar instance = Calendar.getInstance();
        // 默认1小时过期
        instance.add(Calendar.HOUR, 1);

        //创建jwt builder
        JWTCreator.Builder builder = JWT.create();

        // payload
        map.forEach((k, v) -> {
            builder.withClaim(k, v);
        });
        String token = builder.withExpiresAt(instance.getTime())  //指定令牌过期时间
                .sign(Algorithm.HMAC256(SING));  // sign
        return token;
    }

    /**
     * 验证token  合法性
     */
    public static DecodedJWT verify(String token) {
        return JWT.require(Algorithm.HMAC256(SING)).build().verify(token);
    }

    /**
     * 获取token中的id
     */
    public static String getIdFromToken(String token) {
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(SING)).build().verify(token);
        return decodedJWT.getClaim("id").asString();
    }

    /**
     * 获取token中的role
     */
    public static String getRoleFromToken(String token) {
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(SING)).build().verify(token);
        return decodedJWT.getClaim("role").asString();
    }
}


