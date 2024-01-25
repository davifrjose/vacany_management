package br.com.davifrjose.vacany_management.modules.candidate.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.davifrjose.vacany_management.exceptions.UserNotFoundException;
import br.com.davifrjose.vacany_management.modules.candidate.CandidateRepository;
import br.com.davifrjose.vacany_management.modules.candidate.entity.ApplyJobEntity;
import br.com.davifrjose.vacany_management.modules.candidate.repository.ApplyJobRepository;
import br.com.davifrjose.vacany_management.modules.company.repositories.JobRepository;

@Service
public class ApplyJobCandidateUseCase {

  @Autowired
  private CandidateRepository candidateRepository;

  @Autowired
  private JobRepository jobRepository;

  @Autowired
  private ApplyJobRepository  applyJobRepository;

  // candidate_id , vacany_id
  public ApplyJobEntity execute(UUID candidateId, UUID jobId){
      // validate  if candidate exists
      this.candidateRepository.findById(candidateId)
        .orElseThrow(()-> {
          throw new UserNotFoundException();
        });

      // validate if job exists
      this.jobRepository.findById(jobId)
        .orElseThrow(()-> {
          throw new JobNotFoundException();
        });
        
      // candidate applys for the job
      var applyJob = ApplyJobEntity.builder()
      .candidateId(candidateId)
      .jobId(jobId)
      .build();


      applyJob = this.applyJobRepository.save(applyJob);

      return applyJob;
  }
  
}
