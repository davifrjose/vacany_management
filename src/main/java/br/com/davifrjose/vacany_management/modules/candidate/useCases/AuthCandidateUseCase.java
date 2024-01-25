package br.com.davifrjose.vacany_management.modules.candidate.useCases;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.davifrjose.vacany_management.modules.candidate.CandidateRepository;
import br.com.davifrjose.vacany_management.modules.candidate.dto.AuthCandidateRequestDTO;
import br.com.davifrjose.vacany_management.modules.candidate.dto.AuthCandidateResponseDTO;

@Service
public class AuthCandidateUseCase {

  @Autowired
  CandidateRepository candidateRepository;

  @Autowired
  PasswordEncoder passwordEncoder;

  @Value("${security.token.secret.candidate}")
  private String secreteKey;
  
  public AuthCandidateResponseDTO execute(AuthCandidateRequestDTO authCandidateRequestDTO) throws AuthenticationException{

    var candidate = this.candidateRepository.findByUserName(authCandidateRequestDTO.userName())
      .orElseThrow(()-> {
        throw new UsernameNotFoundException("Username/password incorrect");
      });

      // Verify the password
      var passwordMatches = this.passwordEncoder.matches(authCandidateRequestDTO.password(), candidate.getPassword());
         //if != error
         if(!passwordMatches)
         {
          throw new AuthenticationException();
         }
         // if == token
         Algorithm algorithm = Algorithm.HMAC256(secreteKey);
         var expiresIn = Instant.now().plus(Duration.ofMinutes(10));
         var token = JWT.create().withIssuer("javagas")        
         .withSubject(candidate.getId().toString())
         .withClaim("roles", Arrays.asList("CANDIDATE"))
         .withExpiresAt(Instant.now().plus(Duration.ofMinutes(10)))
         .sign(algorithm);

         var authCandidateResponseDTO = AuthCandidateResponseDTO.builder()
          .access_token(token)
          .expires_in(expiresIn.toEpochMilli())
          .build();

          return authCandidateResponseDTO;

  }
}
