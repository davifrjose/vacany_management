package br.com.davifrjose.vacany_management.utils;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.UUID;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestUtils {

  

  public static String objectToJson(Object object){

    try {
      final ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.writeValueAsString(object);
    } catch (Exception e) {
      // TODO: handle exception
      throw new RuntimeException(e);
    }
    

  }

  public static String generateToken(UUID idCompany, String secret){
     Algorithm algorithm = Algorithm.HMAC256(secret);



      var expiresIn =  Instant.now().plus(Duration.ofHours(2));

      var token = JWT.create().withIssuer("javagas")
      .withExpiresAt(expiresIn)
      .withSubject(idCompany.toString())
      .withClaim("roles", Arrays.asList("COMPANY"))
      .sign(algorithm);

      return token;
  }

    
  
}