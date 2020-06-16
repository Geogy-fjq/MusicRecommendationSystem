//package com.horizon.common.util;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.*;
//import java.util.Arrays;
//import java.util.Date;
//import java.util.UUID;
//
///**
// * Created by taos on 2018/1/26.
// */
//public class JWTUtils {
//    private JWTUtils(){}
//    public static void main(String[] args) {
//        System.out.println(createJWT("1", "taos", "zhang", 100));
//    }
//    public static String createJWT(String id, String issuer, String subject, long ttlMillis) {
//
//        Claims claims = Jwts.claims().setSubject("user_taos");
//        claims.put("scopes", Arrays.asList(Scopes.REFRESH_TOKEN.authority()));
//
//        String token = Jwts.builder()
//                .setClaims(claims)
//                .setIssuer("taos")
//                .setId(UUID.randomUUID().toString())
//                .setIssuedAt(new Date())
//                .setExpiration(new Date())
//                .signWith(SignatureAlgorithm.HS512, "abcd")
//                .compact();
//
//        return new AccessJwtToken(token, claims);
//    }
//}
