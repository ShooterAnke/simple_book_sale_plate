package net.anke.books_sales_platform.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import net.anke.books_sales_platform.model.entity.User;

import java.util.Date;

public class JWTUtil {
/**
     * 过期时间
     */

    private static final long EXPIRE=60000*60*24;//过期时间1周
/**
     * 秘钥
     */

    private static final String SECRET="ANKE";
/**
     * 令牌前缀
     */

    private static final String TOKEN_PREFIX="anke";
/**
     * 颁布者
     */

    private static final String SUBJECT="anke";

/**
     * 根据用户信息生成令牌
     * @param username
     * @return
     */

    public static String geneJsonWebToken(String username)
    {
/**
         * 设置payload
         */

        String token= Jwts.builder().setSubject(SUBJECT)
                .claim("username",username).setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+EXPIRE)).signWith(SignatureAlgorithm.HS256,SECRET).compact();
        token=TOKEN_PREFIX+token;
        return token;

    }

/**
     * 校验token
     * @param token
     * @return
     */

    public static Claims checkJWT(String token)
    {
        try{
            final Claims claims= Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token.replace(TOKEN_PREFIX,"")).getBody();
            return  claims;
        }
        catch (Exception e)
        {
            return null;
        }
    }
}

