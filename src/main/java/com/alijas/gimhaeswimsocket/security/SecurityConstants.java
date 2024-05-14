package com.alijas.gimhaeswimsocket.security;


// TODO : 수정 필요
public final class SecurityConstants {
    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String RULE_KEY = "scope";
    public static final String TOKEN_TYPE = "type";


    public static long accessTokenDurationHour = 1L;
    public static long refreshTokenDurationDay = 7L;
    public static String SECRET_KEY = "ghswim";

    public static final String[] ADMIN = {

    };

    private SecurityConstants() {
    }

}