package com.example.myauth2.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.myauth2.entity.Scope;

import java.util.Date;
import java.util.List;
import java.util.Set;

//helper class for handling with jwt token
public class JwtUtil {
    private static Algorithm algorithm;
    private static JWTVerifier verifier;
    /*this is jwt secret key use to encode jwt token only backend server hold this key
     * if an attacker know this key his can modify jwt token in the right way to grant access to api
     */
    private static final String JWT_SECRET_KEY = "secret";
    //some time units constant
    public static final int ONE_SECOND = 1000;
    public static final int ONE_MINUTE = ONE_SECOND * 60;
    public static final int ONE_HOUR = ONE_MINUTE * 60;
    public static final int ONE_DAY = ONE_HOUR * 24;
    public static final String SCOPE_CLAIM_KEY = "scopes";

    public static Algorithm getAlgorithm() {
        if (algorithm == null) {
            algorithm = Algorithm.HMAC256(JWT_SECRET_KEY.getBytes());
        }
        return algorithm;
    }

    public static JWTVerifier getVerifier() {
        if(verifier == null) {
            verifier = JWT.require(getAlgorithm()).build();
        }
        return verifier;
    }

    public static DecodedJWT getDecodedJwt(String token){
        DecodedJWT decodedJWT = getVerifier().verify(token);
        return decodedJWT;
    }

    public static String generateToken(String subject, String username,List<String> scopes, int expireAfter) {
        if(scopes == null || scopes.size() == 0) {
            return JWT.create()
                    .withSubject(subject)
                    .withClaim("username", username)
                    .withExpiresAt(new Date(System.currentTimeMillis() + expireAfter))
//                    .withIssuer(issuer)
                    .sign(getAlgorithm());
        }
        return JWT.create()
                .withSubject(subject)
                .withExpiresAt(new Date(System.currentTimeMillis() + expireAfter))
//                .withIssuer(issuer)
                // when role n -> n user
                .withClaim("username", username)
                .withClaim(JwtUtil.SCOPE_CLAIM_KEY, scopes)
                //when role n -> 1 user
//                .withClaim(JwtUtil.ROLE_CLAIM_KEY, role) //get first role in Authorities
                .sign(getAlgorithm());
    }
}
