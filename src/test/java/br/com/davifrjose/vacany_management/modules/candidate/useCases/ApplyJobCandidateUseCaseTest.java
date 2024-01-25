package br.com.davifrjose.vacany_management.modules.candidate.useCases;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.davifrjose.vacany_management.exceptions.UserNotFoundException;
import br.com.davifrjose.vacany_management.modules.candidate.CandidateEntity;
import br.com.davifrjose.vacany_management.modules.candidate.CandidateRepository;
import br.com.davifrjose.vacany_management.modules.candidate.entity.ApplyJobEntity;
import br.com.davifrjose.vacany_management.modules.candidate.repository.ApplyJobRepository;
import br.com.davifrjose.vacany_management.modules.company.entities.JobEntity;
import br.com.davifrjose.vacany_management.modules.company.repositories.JobRepository;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class ApplyJobCandidateUseCaseTest {


  @InjectMocks
  private ApplyJobCandidateUseCase applyJobCandidateUseCase;

  @Mock
  private CandidateRepository candidateRepository;

  @Mock
  private JobRepository jobRepository;

  @Mock
  private ApplyJobRepository applyJobRepository;
  
  @Test
  @DisplayName("Should Not Be Able To Apply Job With Candidate Not Found")
  public void shouldNotBeAbleToApplyJobWithCandidateNotFound()
  {
    try {
      applyJobCandidateUseCase.execute(null, null);
    } catch (Exception e) {
      // TODO: handle exception
      assertThat(e).isInstanceOf(UserNotFoundException.class);
    }

    
  }

  @Test
  public void shouldNotBeAbleToApplyJobWithJobNotFound(){
    var candidateId = UUID.randomUUID();

    var candidate = new CandidateEntity();
    candidate.setId(candidateId);

    when(candidateRepository.findById(candidateId)).thenReturn(Optional.of(candidate));

     try {
      applyJobCandidateUseCase.execute(candidateId, null);
    } catch (Exception e) {
      // TODO: handle exception
      assertThat(e).isInstanceOf(JobNotFoundException.class);
    }

  }

  @Test 
  public void shouldBeAbleToCreateNewApplyJob()
  {
    var candidadeId = UUID.randomUUID();
    var jobId = UUID.randomUUID();

    var applyJob = ApplyJobEntity.builder()
      .candidateId(candidadeId)
      .jobId(jobId)
      .build();

    var applyJobCreated = ApplyJobEntity.builder().id(UUID.randomUUID()).build();

    when(candidateRepository.findById(candidadeId)).thenReturn(Optional.of(new CandidateEntity()));
    when(jobRepository.findById(jobId)).thenReturn(Optional.of(new JobEntity()));

    when(applyJobRepository.save(applyJob)).thenReturn(applyJobCreated);

    var result = applyJobCandidateUseCase.execute(candidadeId, jobId);

    assertThat(result).hasFieldOrProperty("id");
    assertNotNull(result.getId());
  }
}
