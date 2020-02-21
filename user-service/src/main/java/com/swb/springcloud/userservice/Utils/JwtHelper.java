package com.swb.springcloud.userservice.Utils;

import java.util.Date;
import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.common.collect.Maps;
import org.apache.commons.lang.time.DateUtils;

public class JwtHelper {
  
  private static final String  SECRET = "session_secret";
  
  private static final String  ISSUER = "swb_user";//发布者
  
  //生成token
  public static String genToken(Map<String, String> claims){
    try {
      //声明token生成算法
      Algorithm algorithm = Algorithm.HMAC256(SECRET);
      JWTCreator.Builder builder = JWT.create().withIssuer(ISSUER)
              .withExpiresAt(DateUtils.addDays(new Date(), 1));//过期时间1天
      //需要保存的数据存储到token中
      claims.forEach((k,v) -> builder.withClaim(k, v));
      return builder.sign(algorithm).toString();
    } catch (IllegalArgumentException e) {
      throw new RuntimeException(e);
    }
  }
  
  public static Map<String, String> verifyToken(String token)  {
    Algorithm algorithm = null;
    try {
      algorithm = Algorithm.HMAC256(SECRET);
    } catch (IllegalArgumentException e) {
      throw new RuntimeException(e);
    }
    JWTVerifier verifier = JWT.require(algorithm).withIssuer(ISSUER).build();
    DecodedJWT jwt =  verifier.verify(token);
    Map<String, Claim> map = jwt.getClaims();
    Map<String, String> resultMap = Maps.newHashMap();
    map.forEach((k,v) -> resultMap.put(k, v.asString()));
    return resultMap;
  }

}
