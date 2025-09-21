package com.WHY.lease.common.utils;

import com.WHY.lease.common.exception.LeaseException;
import com.WHY.lease.common.result.ResultCodeEnum;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;

public class JwtUtil {

    private static SecretKey secretKey = Keys.hmacShaKeyFor("M0PKKI6pYGVWWfDZw90a0lTpGYX1d6AQ".getBytes());

    public static String createToken(Long userId,String username){


        String jwt= Jwts.builder().setExpiration(new Date(System.currentTimeMillis()+3600000*24)).setSubject("LOGIN_USER")
                .claim("userId",userId)
                .claim("username",username)
                .signWith(secretKey, SignatureAlgorithm.HS256).compact();
        return jwt;
    }


    public static Claims parseToken(String token){
        if(token==null)throw new LeaseException(ResultCodeEnum.ADMIN_LOGIN_AUTH);

        try{
            JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(secretKey).build();
            Jws<Claims> claimsJws = jwtParser.parseClaimsJws(token);
             return claimsJws.getBody();
        } catch (ExpiredJwtException e){
            throw new LeaseException(ResultCodeEnum.TOKEN_EXPIRED);
        }catch (JwtException e){
            throw new LeaseException(ResultCodeEnum.TOKEN_INVALID);
        }

    }


    public static void main(String args[]){
        System.out.println(createToken(8L,"用户-1187"));
    }




}
