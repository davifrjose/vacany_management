package br.com.davifrjose.vacany_management.modules.candidate.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import br.com.davifrjose.vacany_management.exceptions.UserNotFoundException;
import br.com.davifrjose.vacany_management.modules.candidate.CandidateRepository;
import br.com.davifrjose.vacany_management.modules.candidate.dto.ProfileCandidateResponseDTO;
import br.com.davifrjose.vacany_management.modules.candidate.dto.ProfileCandidateResponseDTO.ProfileCandidateResponseDTOBuilder;

@Service
public class ProfileCandidateUseCase {
  
  @Autowired
  private CandidateRepository candidateRepository;
  public ProfileCandidateResponseDTOBuilder execute(UUID idCandidate){
   var candidate =  this.candidateRepository.findById(idCandidate)
   .orElseThrow(()-> {
    throw new UserNotFoundException();
   });

   var candidateDTO =  ProfileCandidateResponseDTO.builder()
    .description(candidate.getDescription())
    .userName(candidate.getUserName())
    .email(candidate.getEmail())
    .name(candidate.getName())
    .id(candidate.getId());

    return candidateDTO;
  }
}
