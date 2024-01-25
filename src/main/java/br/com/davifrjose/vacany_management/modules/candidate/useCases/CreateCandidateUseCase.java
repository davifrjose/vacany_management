package br.com.davifrjose.vacany_management.modules.candidate.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.davifrjose.vacany_management.exceptions.UserFoundException;
import br.com.davifrjose.vacany_management.modules.candidate.CandidateEntity;
import br.com.davifrjose.vacany_management.modules.candidate.CandidateRepository;

@Service
public class CreateCandidateUseCase {
  
  @Autowired
  private CandidateRepository candidateRepository;

  @Autowired
  private PasswordEncoder passwordEncoder ;

  public CandidateEntity execute (CandidateEntity candidateEntity){
 this.candidateRepository.
    findByUserNameOrEmail(candidateEntity.getUserName(),candidateEntity.getEmail())
    .ifPresent((user) -> {
      throw new UserFoundException();
    });

    var password = passwordEncoder.encode(candidateEntity.getPassword());
    candidateEntity.setPassword(password);

    return this.candidateRepository.save(candidateEntity);

  }
}
