package br.com.davifrjose.vacany_management.modules.company.useCases;

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

import br.com.davifrjose.vacany_management.modules.company.dto.AuthCompanyDTO;
import br.com.davifrjose.vacany_management.modules.company.dto.AuthCompanyResponseDTO;

import br.com.davifrjose.vacany_management.modules.company.repositories.CompanyRepository;

@Service
public class AuthCompanyUseCase {
  
  @Value("${security.token.secret}")
  private String secreteKey;

  @Autowired
  private CompanyRepository companyRepository;

  @Autowired
  PasswordEncoder passwordEncoder;
  
  public AuthCompanyResponseDTO execute(AuthCompanyDTO authCompanyDTO) throws AuthenticationException{
    var company = this.companyRepository.findByUserName(authCompanyDTO.getUserName()).orElseThrow(
      ()-> {
        throw new UsernameNotFoundException("Username/password incorrect");
      }
    );

    // Verify the password
    var passwordMatches = this.passwordEncoder.matches(authCompanyDTO.getPassword(), company.getPassword());
      //if != error
      if(!passwordMatches)
      {
        throw new AuthenticationException();
      }
      // if == token
      Algorithm algorithm = Algorithm.HMAC256(secreteKey);



      var expiresIn =  Instant.now().plus(Duration.ofHours(2));

      var token = JWT.create().withIssuer("javagas")
      .withExpiresAt(Instant.now().plus(Duration.ofHours(2)))
      .withSubject(company.getId().toString())
      .withClaim("roles", Arrays.asList("COMPANY"))
      .sign(algorithm);

      var authCompanyResponseDTO =  AuthCompanyResponseDTO.builder()
        .access_token(token)
        .expires_in(expiresIn.toEpochMilli())
        .build()
        ;

      return authCompanyResponseDTO;
  }
}
