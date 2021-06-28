package com.ex.server.util;

import com.ex.server.constant.IConstant;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultClaims;
import io.jsonwebtoken.impl.DefaultHeader;
import io.jsonwebtoken.impl.DefaultJwsHeader;
import io.jsonwebtoken.impl.TextCodec;
import io.jsonwebtoken.impl.compression.DefaultCompressionCodecResolver;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.lang.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class JwtTokenUtil {
    private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CLAIM_KEY_CREATED = "created";

    @Value("${jwt.secret}")
    private String secret ;

    @Value("${jwt.expiration}")
    private Long expiration;

    /**
     * 生成token
     * @param claims
     * @param expiration 过期时间，单位是秒
     * @return
     */
    public String generateToken(Map<String,Object> claims,Long expiration){
        return Jwts.builder().setClaims(claims)
                .setExpiration(IDateUtil.generateDefaultTokenExpiration(expiration))//也是一个claims，key为exp
                .signWith(SignatureAlgorithm.HS256,secret)//设置加密方式和密钥
                .compact();
    }

    /**
     * 生成验证码的令牌
     * @param text
     * @return
     */
    public String generateValidateCodeToken(String text){
        Map<String,Object> claims = new HashMap<>(16);
        claims.put(CLAIM_KEY_CREATED,new Date());//设置创建时间
        claims.put(IConstant.VERIFY_CODE,text);
        return generateToken(claims,expiration);
    }

    /**
     * 生成用户的token
     * @param userDetails
     * @return
     */
    public String generateUsernameToken(UserDetails userDetails){
        Map<String,Object> claims = new HashMap<>(16);
        claims.put(CLAIM_KEY_CREATED,new Date());//设置创建时间
        claims.put(CLAIM_KEY_USERNAME,userDetails.getUsername());
        return generateToken(claims,expiration);
    }

    /**
     * 验证用户token
     * @param token
     * @param username
     * @return
     */
    public boolean validateUsernameToken(String token ,String username){
        Claims claims = parseToken(token);
        if (claims!=null) {
            return username.equals(claims.get(CLAIM_KEY_USERNAME));
        }
        return false;
    }

    /**
     * 从token中获取用户名
     * @param token
     * @return
     */
    public String getUsernameByToken(String token){
        Claims claims = parseToken(token);
        String username = null;
        if(claims!=null){
            username = claims.getSubject();
        }
        return username;
    }

    /**
     * 解析token
     * @param token
     * @return
     */
    public Claims parseToken(String token){
        Claims claims = null;
        try {
            claims = Jwts.parser().setSigningKey(secret)
                    .parseClaimsJws(token).getBody();
        } catch (Exception e) {
            log.error("出现了异常：{}",CommonUtil.getThrowableMessage(e));
        }
        return claims;
    }

    /**
     * 验证token是否有效
     * @param token
     * @return true表示有效，false表示已过期或者解析错误
     */
    public boolean isTokenValid(String token){
        return null != parseToken(token);
    }

    /**
     * 刷新已经过期的token
     * @param token
     * @return
     */
    public String refreshToken(String token){
        try {
            Claims claims = getTokenBody(token);
            claims.put(CLAIM_KEY_CREATED,new Date());
            //claims.setExpiration(IDateUtil.generateDefaultTokenExpiration(expiration));
            return generateToken(claims,expiration);
        } catch (JsonProcessingException e) {
            log.error("解析token出错，请检查：{}",token);
        }
        return null;
    }

    /**
     * 从源码拷贝过来的，只用于解析token获取Claims，无论过没过期
     * @param jwt token
     * @return
     */
    public Claims getTokenBody(String jwt) throws JsonProcessingException {
        Assert.hasText(jwt, "JWT String argument cannot be null or empty.");

        String base64UrlEncodedHeader = null;
        String base64UrlEncodedPayload = null;
        String base64UrlEncodedDigest = null;

        int delimiterCount = 0;

        StringBuilder sb = new StringBuilder(128);

        for (char c : jwt.toCharArray()) {

            if (c == '.') {

                CharSequence tokenSeq = Strings.clean(sb);
                String token = tokenSeq!=null?tokenSeq.toString():null;

                if (delimiterCount == 0) {
                    base64UrlEncodedHeader = token;
                } else if (delimiterCount == 1) {
                    base64UrlEncodedPayload = token;
                }

                delimiterCount++;
                sb.setLength(0);
            } else {
                sb.append(c);
            }
        }

        if (delimiterCount != 2) {
            String msg = "JWT strings must contain exactly 2 period characters. Found: " + delimiterCount;
            throw new MalformedJwtException(msg);
        }
        if (sb.length() > 0) {
            base64UrlEncodedDigest = sb.toString();
        }

        if (base64UrlEncodedPayload == null) {
            throw new MalformedJwtException("JWT string '" + jwt + "' is missing a body/payload.");
        }

        // =============== Header =================
        Header header = null;

        CompressionCodec compressionCodec = null;

        ObjectMapper objectMapper = new ObjectMapper();

        if (base64UrlEncodedHeader != null) {
            String origValue = TextCodec.BASE64URL.decodeToString(base64UrlEncodedHeader);
            Map<String, Object> m = objectMapper.readValue(origValue, Map.class);

            if (base64UrlEncodedDigest != null) {
                header = new DefaultJwsHeader(m);
            } else {
                header = new DefaultHeader(m);
            }

            compressionCodec = new DefaultCompressionCodecResolver().resolveCompressionCodec(header);
        }

        // =============== Body =================
        String payload;
        if (compressionCodec != null) {
            byte[] decompressed = compressionCodec.decompress(TextCodec.BASE64URL.decode(base64UrlEncodedPayload));
            payload = new String(decompressed, Strings.UTF_8);
        } else {
            payload = TextCodec.BASE64URL.decodeToString(base64UrlEncodedPayload);
        }

        Claims claims = null;

        if (payload.charAt(0) == '{' && payload.charAt(payload.length() - 1) == '}') { //likely to be json, parse it:
            Map<String, Object> claimsMap = objectMapper.readValue(payload,Map.class);
            claims = new DefaultClaims(claimsMap);
        }

        return claims;
    }

}
